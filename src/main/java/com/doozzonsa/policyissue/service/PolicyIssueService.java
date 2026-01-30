package com.doozzonsa.policyissue.service;

import com.doozzonsa.policyissue.domain.IssueType;
import com.doozzonsa.policyissue.domain.PolicyIssue;
import com.doozzonsa.policyissue.repository.PolicyIssueRepository;
import com.doozzonsa.policyissue.service.dto.PolicyIssueDto;
import com.doozzonsa.policyissue.service.dto.PolicyIssuesDto;
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

    public PolicyIssuesDto readAll() {
        // 이슈 목록 조회 (최신순)
        List<PolicyIssue> policyIssues = policyIssueRepository.findAllByOrderByIssueDateDesc();

        // 통계 정보 계산 (조회 결과 기반)
        int totalCount = policyIssues.size();
        int thisMonthCount = countThisMonthIssues(policyIssues);
        int dataBreachCount = countByIssueType(policyIssues, IssueType.DATA_BREACH);
        int abuseCount = countByIssueType(policyIssues, IssueType.ABUSE);

        StatisticsDto statistics = new StatisticsDto(
                totalCount,
                thisMonthCount,
                dataBreachCount,
                abuseCount
        );

        List<PolicyIssueDto> policyIssueDtos = policyIssues.stream()
                .map(PolicyIssueDto::from)
                .toList();

        // 이번 주 요약 생성
        String thisWeekSummary = generateThisWeekSummary();

        return new PolicyIssuesDto(
                thisWeekSummary,
                policyIssueDtos,
                statistics
        );
    }

    private int countThisMonthIssues(List<PolicyIssue> policyIssues) {
        YearMonth currentMonth = YearMonth.now();
        LocalDate startOfMonth = currentMonth.atDay(1);
        LocalDate endOfMonth = currentMonth.atEndOfMonth();

        return (int) policyIssues.stream()
                .map(PolicyIssue::getIssueDate)
                .filter(issueDate -> !issueDate.isBefore(startOfMonth) && !issueDate.isAfter(endOfMonth))
                .count();
    }

    private int countByIssueType(List<PolicyIssue> policyIssues, IssueType issueType) {
        return (int) policyIssues.stream()
                .filter(policyIssue -> policyIssue.getIssueType() == issueType)
                .count();
    }

    private String generateThisWeekSummary() {
        return "이번 주 가장 주의할 소식은 공공·생활 서비스 전반에서 발생한 개인정보 유출이에요. \n"
                + "따릉이와 티머니, 대학·항공사까지 이름과 연락처, 생년월일 같은 정보가 대규모로 빠져나갔어요. \n"
                + "지금 사용 중인 서비스들의 개인정보 처리방침을 한 번 점검해보는 게 좋아요.";
    }
}
