package com.doozzonsa.policyitem.service.dto;

import java.util.List;

public record PolicyItemStatisticsDto(
        List<PolicyItemDto> policyItems,
        List<RiskLevelDto> riskLevels
) {
}
