package com.doozzonsa.enterprise.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.doozzonsa.enterprise.domain.Enterprise;
import com.doozzonsa.enterprise.domain.EnterprisePolicyItem;
import com.doozzonsa.enterprise.domain.IndustryType;
import com.doozzonsa.enterprise.domain.Scale;
import com.doozzonsa.enterprise.repository.EnterprisePolicyItemRepository;
import com.doozzonsa.enterprise.repository.EnterpriseRepository;
import com.doozzonsa.policyitem.domain.PolicyItem;
import com.doozzonsa.policyitem.domain.repository.PolicyItemRepository;
import com.doozzonsa.util.normalizer.PolicyItemNormalizer;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional
public class EnterprisePolicySeedRunner implements CommandLineRunner {

	private final EnterpriseRepository enterpriseRepository;
	private final PolicyItemRepository policyItemRepository;
	private final EnterprisePolicyItemRepository enterprisePolicyItemRepository;

	@Override
	public void run(String... args) throws Exception {

		// TSV 파일 로드
		var lines = readResourceLines("dummies/enterprise_agreements_dummy.tsv");

		// 각 기업에 대해 기업 upsert, 약관 키워드 upsert, 매핑 생성
		for (String line : lines) {
			if (line.isBlank()) continue;

			// tsv 파일 탭으로 파싱
			String[] parts = line.split("\t");
			String enterpriseName = parts[0].trim();
			String rawItems = parts[1].trim();
			Scale scale = Scale.valueOf(parts[2].trim());
			String country = parts[3].trim();
			IndustryType industryType = IndustryType.valueOf(parts[4].trim());


			// 기업 upsert
			Enterprise enterprise = enterpriseRepository.findByName(enterpriseName)
				.orElseGet(()-> enterpriseRepository.save(
					Enterprise.create(enterpriseName, scale, country, industryType)
				));

			// 약관 키워드들 일관된 표현으로 정규화
			Set<String> canonicalItems = Arrays.stream(rawItems.split(","))
				.map(String::trim)
				.filter(s -> !s.isBlank())
				.map(PolicyItemNormalizer::normalize)
				.collect(Collectors.toCollection(LinkedHashSet::new));

			// 약관 키워드 upsert
			Map<String, PolicyItem> policyItemMap = new HashMap<>();
			for (String itemName : canonicalItems) {
				PolicyItem pi = policyItemRepository.findByName(itemName)
					.orElseGet(() -> policyItemRepository.save(
						PolicyItem.create(itemName, null)
					));
				policyItemMap.put(itemName, pi);
			}

			// 매핑 생성
			List<EnterprisePolicyItem> mappings = canonicalItems.stream()
				.map(itemName -> EnterprisePolicyItem.create(enterprise, policyItemMap.get(itemName)))
				.toList();

			enterprisePolicyItemRepository.saveAllIgnoreDuplicates(mappings);
		}
	}

	private List<String> readResourceLines(String path) throws IOException {
		try (InputStream is = new ClassPathResource(path).getInputStream();
			 BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
			return br.lines().toList();
		}
	}
}