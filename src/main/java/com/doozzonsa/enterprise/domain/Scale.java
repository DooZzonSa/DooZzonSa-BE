package com.doozzonsa.enterprise.domain;

public enum Scale {

    LARGE("대기업"),
    MID("중견/유니콘"),
    LOW("중소"),
    ;

    private final String name;

    Scale(final String name) {
        this.name = name;
    }
}
