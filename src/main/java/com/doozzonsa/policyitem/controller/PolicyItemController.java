package com.doozzonsa.policyitem.controller;

import com.doozzonsa.global.response.dto.SuccessResponse;
import com.doozzonsa.policyitem.service.PolicyItemService;
import com.doozzonsa.policyitem.service.dto.PolicyItemStatisticsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PolicyItemController implements PolicyItemControllerDocs {

    private final PolicyItemService policyItemService;

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
