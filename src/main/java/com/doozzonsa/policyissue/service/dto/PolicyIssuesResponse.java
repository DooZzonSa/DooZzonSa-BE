package com.doozzonsa.policyissue.service.dto;

import java.util.List;

public record PolicyIssuesResponse(
        String thisWeekSummary,
        List<PolicyIssueDto> policyIssues,
        StatisticsDto statistics
) {
}
