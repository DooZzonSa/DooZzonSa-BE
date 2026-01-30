package com.doozzonsa.policyissue.service.dto;

public record StatisticsDto(
        int totalCount,
        int thisMonthCount,
        int dataBreachCount,
        int abuseCount
) {
}
