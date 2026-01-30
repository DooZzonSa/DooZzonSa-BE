package com.doozzonsa.policyissue.controller;

import com.doozzonsa.global.response.dto.SuccessResponse;
import com.doozzonsa.policyissue.service.PolicyIssueService;
import com.doozzonsa.policyissue.service.dto.PolicyIssuesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/policy-issues")
public class PolicyIssueController {

    private final PolicyIssueService policyIssueService;

    @GetMapping
    public ResponseEntity<SuccessResponse<PolicyIssuesResponse>> readAll() {
        PolicyIssuesResponse data = policyIssueService.readAll();

        SuccessResponse<PolicyIssuesResponse> response = new SuccessResponse<>(
                200,
                "악용 이슈 조회 성공",
                data
        );

        return ResponseEntity.ok(response);
    }
}
