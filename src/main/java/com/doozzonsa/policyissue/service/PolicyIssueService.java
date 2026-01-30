package com.doozzonsa.policyissue.service;

import com.doozzonsa.policyissue.domain.IssueType;
import com.doozzonsa.policyissue.domain.PolicyIssue;
import com.doozzonsa.policyissue.repository.PolicyIssueRepository;
import com.doozzonsa.policyissue.service.dto.PolicyIssueDto;
import com.doozzonsa.policyissue.service.dto.PolicyIssuesResponse;
import com.doozzonsa.policyissue.service.dto.StatisticsDto;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PolicyIssueService {

    private final PolicyIssueRepository policyIssueRepository;

    public PolicyIssuesResponse readAll() {
        // 통계 정보 조회
        int totalCount = countAllIssues();
        int thisMonthCount = countThisMonthIssues();
        int dataBreachCount = countByIssueType(IssueType.DATA_BREACH);
        int abuseCount = countByIssueType(IssueType.ABUSE);

        StatisticsDto statistics = new StatisticsDto(
                totalCount,
                thisMonthCount,
                dataBreachCount,
                abuseCount
        );

        // 이슈 목록 조회 (최신순)
        List<PolicyIssue> policyIssues = policyIssueRepository.findAllByOrderByIssueDateDesc();
        List<PolicyIssueDto> policyIssueDtos = policyIssues.stream()
                .map(PolicyIssueDto::from)
                .toList();

        // 이번 주 요약 생성
        String thisWeekSummary = generateThisWeekSummary();

        return new PolicyIssuesResponse(
                thisWeekSummary,
                policyIssueDtos,
                statistics
        );
    }

    private int countAllIssues() {
        return (int) policyIssueRepository.count();
    }

    private int countThisMonthIssues() {
        YearMonth currentMonth = YearMonth.now();
        LocalDate startOfMonth = currentMonth.atDay(1);
        LocalDate endOfMonth = currentMonth.atEndOfMonth();

        return policyIssueRepository.countByIssueDateBetween(startOfMonth, endOfMonth);
    }

    private int countByIssueType(IssueType issueType) {
        return policyIssueRepository.countByIssueType(issueType);
    }

    private String generateThisWeekSummary() {
        return "이번 주 가장 주의할 소식은 A쇼핑몰 고객정보 유출이에요. 이름, 연락처, 주소가 빠져나갔어요. "
                + "B금융앱은 동의 없이 광고사에 정보를 팔다 적발됐고요. 가입된 서비스 약관, 한 번 점검해보시는 게 좋겠어요.";
    }
}
