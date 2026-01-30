package com.doozzonsa.policyissue.service.dto;

import com.doozzonsa.policyissue.domain.PolicyIssue;

public record PolicyIssueDto(
        String title,
        String summary,
        String source,
        String issueType,
        String url
) {
    public static PolicyIssueDto from(PolicyIssue policyIssue) {
        return new PolicyIssueDto(
                policyIssue.getTitle(),
                policyIssue.getSummary(),
                policyIssue.getSource(),
                policyIssue.getIssueType().getDescription(),
                policyIssue.getUrl()
        );
    }
}
