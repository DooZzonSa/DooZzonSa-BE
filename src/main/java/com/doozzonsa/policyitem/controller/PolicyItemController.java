package com.doozzonsa.policyitem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doozzonsa.global.response.dto.SuccessResponse;
import com.doozzonsa.policyitem.controller.dto.PolicyAnalysisRequest;
import com.doozzonsa.policyitem.controller.dto.PolicyAnalysisResponse;
import com.doozzonsa.policyitem.exception.PolicyItemSuccessCode;
import com.doozzonsa.policyitem.service.PolicyAiService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/policy-items")
public class PolicyItemController {

	private final PolicyAiService policyAiService;

	@PostMapping
	ResponseEntity<SuccessResponse<PolicyAnalysisResponse>> analysisPolicy(
		@Valid @RequestBody PolicyAnalysisRequest request
	) {
		PolicyAnalysisResponse response = policyAiService.getAnalyzedPolicy(request.context());

		return ResponseEntity.ok()
			.body(SuccessResponse.of(PolicyItemSuccessCode.POLICY_ANALYSIS_SUCCESS, response));
	}
}
