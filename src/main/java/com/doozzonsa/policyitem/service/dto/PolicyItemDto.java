package com.doozzonsa.policyitem.service.dto;

public record PolicyItemDto(
        String name,
        int averageCollectionRate,
        String riskLevel
) {
    public PolicyItemDto(final String name, final int averageCollectionRate, final com.doozzonsa.policyitem.domain.RiskLevel riskLevel) {
        this(name, averageCollectionRate, riskLevel.getLevel());
    }
}
