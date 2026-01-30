package com.doozzonsa.policyitem.domain;

import lombok.Getter;

@Getter
public enum RiskLevel {

    HIGH("높음"),
    MID("중간"),
    LOW("낮음"),
    ;

    private final String level;

    RiskLevel(final String level) {
        this.level = level;
    }
}
