package com.doozzonsa.policyitem.controller;

import com.doozzonsa.global.response.dto.SuccessResponse;
import com.doozzonsa.policyitem.controller.dto.PolicyAnalysisRequest;
import com.doozzonsa.policyitem.controller.dto.PolicyAnalysisResponse;
import com.doozzonsa.policyitem.exception.PolicyItemSuccessCode;
import com.doozzonsa.policyitem.service.PolicyAiService;
import com.doozzonsa.policyitem.service.PolicyItemService;
import com.doozzonsa.policyitem.service.dto.PolicyItemStatisticsDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PolicyItemController implements PolicyItemApi {

    private final PolicyAiService policyAiService;
    private final PolicyItemService policyItemService;

    @PostMapping("/policy-items")
    public ResponseEntity<SuccessResponse<PolicyAnalysisResponse>> analysisPolicy(
            @Valid @RequestBody PolicyAnalysisRequest request
    ) {
        PolicyAnalysisResponse response = policyAiService.getAnalyzedPolicy(request.context());

        return ResponseEntity.ok()
                .body(SuccessResponse.of(PolicyItemSuccessCode.POLICY_ANALYSIS_SUCCESS, response));
    }

    @GetMapping("/statistics")
    public ResponseEntity<SuccessResponse<PolicyItemStatisticsDto>> readStatistics() {
        PolicyItemStatisticsDto data = policyItemService.getStatistics();

        SuccessResponse<PolicyItemStatisticsDto> response = new SuccessResponse<>(
                200,
                "수집 통계 조회 성공",
                data
        );

        return ResponseEntity.ok(response);
    }
}
