package com.doozzonsa.policyitem.service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import com.doozzonsa.enterprise.domain.Enterprise;
import com.doozzonsa.enterprise.repository.EnterprisePolicyItemRepository;
import com.doozzonsa.enterprise.repository.EnterpriseRepository;
import com.doozzonsa.policyitem.controller.dto.PolicyAnalysisResponse;
import com.doozzonsa.policyitem.controller.dto.PolicyAnalysisResult;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PolicyAiService {

	private final ChatClient chatClient;
	private final EnterpriseRepository enterpriseRepository;
	private final EnterprisePolicyItemRepository enterprisePolicyItemRepository;

	private static final int TOP_K = 8;

	private static final String SYSTEM_PROMPT = """
		너는 개인정보 처리방침(약관) 텍스트를 분석하는 엔진이다.
		사용자가 제공한 약관에서만 근거를 찾아 답해야 하며, 약관에 없는 내용을 추측하거나 만들어내면 안 된다.
		
		목표:
		1) 약관에 명시되었거나 자동수집/제공받음 등으로 수집될 수 있는 개인정보 항목을 '키워드'로 추출한다.
		2) 사용자가 특히 주의해서 봐야 할 조항/문장을 핵심 위주로 추출한다(요약 형태 가능).
		
		정규화 규칙:
		- 같은 의미는 하나로 통일해서 출력한다.
		  예) "IP주소", "접속아이피정보", "접속 IP", "접속지정보" → "IP주소"
		  예) "쿠키", "쿠키정보", "쿠키·식별자", "추적식별자" → "쿠키·식별자"
		  예) "디바이스ID", "기기식별자", "광고식별자", "앱UUID" → "기기식별자"
		- 출력되는 policyItems는 가능하면 짧은 명사형 키워드로 한다(예: "결제정보", "위치정보", "생체정보").
		
		출력 규칙(매우 중요):
		- 반드시 JSON만 출력한다. 다른 텍스트/설명/코드블럭 금지.
		- JSON 스키마는 아래와 동일해야 한다.
		- policyItems는 중복 제거하고, 3~30개 범위로 출력(가능하면 많이 추출).
		- warnings는 3~8개로 출력하며, 반드시 약관 내용에서 근거가 있는 경우에만 포함한다.
		- expectedIndustryType은 선택지 중 하나만.
		
		출력 JSON 스키마:
		{
		  "policyItems": ["..."],
		  "warnings": ["..."],
		  "expectedIndustryType": "..."
		}
		
		이제 아래 약관을 분석하라.
		""";

	public PolicyAnalysisResponse getAnalyzedPolicy(String policyText) {
		PolicyAnalysisResult result = analyze(policyText);

		// 입력한 약관에서 추출한 아이템들
		Set<String> extractedItems = new LinkedHashSet<>(result.policyItems());

		List<Enterprise> enterprises = enterpriseRepository.findAll();

		// 기업들의 수집 아이템들 가져오기
		List<Long> candidateIds = enterprises.stream()
			.map(Enterprise::getId)
			.toList();

		Map<Long, Set<String>> enterpriseItemsMap = toEnterpriseItemsMap(candidateIds);

		// topK 선정 (Jaccard 방식)
		List<Enterprise> topK = enterprises.stream()
			.sorted((a, b) -> Double.compare(
				jaccard(extractedItems, enterpriseItemsMap.getOrDefault(b.getId(), Set.of())),
				jaccard(extractedItems, enterpriseItemsMap.getOrDefault(a.getId(), Set.of()))
			))
			.limit(TOP_K)
			.toList();

		// topK 기준으로 각 키워드가 포함된 기업 비율 계산
		List<Long> topKIds = topK.stream().map(Enterprise::getId).toList();
		Map<Long, Set<String>> topKItemsMap = toEnterpriseItemsMap(topKIds);

		List<PolicyAnalysisResponse.AgreementsRate> agreements = extractedItems.stream()
			.map(item -> {
				long included = topKIds.stream()
					.filter(id -> topKItemsMap.getOrDefault(id, Set.of()).contains(item))
					.count();
				int rate = topKIds.isEmpty() ? 0 : (int) Math.round(included * 100.0 / topKIds.size());
				return new PolicyAnalysisResponse.AgreementsRate(item, rate);
			})
			.filter(a -> a.averageCollectionRate() > 0 && a.averageCollectionRate() < 100)
			.sorted(Comparator.comparingInt(PolicyAnalysisResponse.AgreementsRate::averageCollectionRate))
			.toList();

		// 중요 사항들 추출
		List<PolicyAnalysisResponse.Content> cautions = result.warnings().stream()
			.map(PolicyAnalysisResponse.Content::new)
			.toList();

		return new PolicyAnalysisResponse(agreements, cautions);
	}

	private PolicyAnalysisResult analyze(String policyText) {
		return chatClient.prompt()
			.system(SYSTEM_PROMPT)
			.user(u -> u.text("""
				[약관 원문]
				%s
				""".formatted(policyText)))
			.call()
			.entity(PolicyAnalysisResult.class);
	}

	/*
	리턴하는 Map의 key : 기업의 id
				value : 그 기업이 수집하는 policy item의 Set
	 */
	private Map<Long, Set<String>> toEnterpriseItemsMap(List<Long> enterpriseIds) {
		if (enterpriseIds.isEmpty()) return Map.of();

		// 기업 아이디와 기업의 수집 항목을 함께 가져옴
		List<Object[]> rows = enterprisePolicyItemRepository
			.findEnterpriseIdAndPolicyItemNameByEnterpriseIds(enterpriseIds);

		Map<Long, Set<String>> map = new HashMap<>();
		for (Object[] row : rows) {
			Long enterpriseId = (Long) row[0];
			String itemName = (String) row[1];
			map.computeIfAbsent(enterpriseId, k -> new HashSet<>()).add(itemName);
		}
		return map;
	}

	/*
	입력된 약관의 항목들과 기업의 항목들을 비교함

	자카드 유사도 (Jaccard Similarity) 구하는 방법
	두 집합 A, B가 있을 때
	J(A, B) = (A와 B의 교집합) / (A와 B의 합집합)
	=> 0.0 ~ 1.0 사이의 점수가 클수록 A, B 간 유사도가 높음
	 */
	private double jaccard(Set<String> a, Set<String> b) {
		if (a.isEmpty() && b.isEmpty()) return 1.0;
		if (a.isEmpty() || b.isEmpty()) return 0.0;

		// 교집합
		Set<String> intersection = new HashSet<>(a);
		intersection.retainAll(b);

		// 합집합
		Set<String> union = new HashSet<>(a);
		union.addAll(b);

		return intersection.size() / (double) union.size();
	}
}
