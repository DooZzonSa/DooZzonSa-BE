package com.doozzonsa.policyitem.domain;

import java.util.List;
import lombok.Getter;

@Getter
public enum RiskLevel {

    HIGH("높음"),
    MID("중간"),
    LOW("낮음"),
    NONE("없음");

    private final String level;

    RiskLevel(final String level) {
        this.level = level;
    }

    public static List<RiskLevel> getValidValues() {
        return List.of(HIGH, MID, LOW);
    }
}
