package com.doozzonsa.policyissue.domain;

public enum IssueType {

    DATA_BREACH("정보 유출"),
    ABUSE("약관 악용"),
    ;

    private final String description;

    IssueType(final String description) {
        this.description = description;
    }
}
