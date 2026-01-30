package com.doozzonsa.policyitem.service.dto;

import com.doozzonsa.policyitem.domain.RiskLevel;
import java.util.List;

public record RiskLevelDto(
        String level,
        List<PolicyItemNameDto> policyItems
) {
    public RiskLevelDto(final RiskLevel level, final List<PolicyItemNameDto> policyItems) {
        this(level.getLevel(), policyItems);
    }
}
