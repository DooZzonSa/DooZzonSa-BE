package com.doozzonsa.global.exception;

import com.doozzonsa.global.response.base.BaseCode;
import lombok.Getter;

@Getter
public class DooZzonSaException extends RuntimeException {
    private final BaseCode baseCode;

    public DooZzonSaException(BaseCode baseCode) {
        super(baseCode.getMessage());
        this.baseCode = baseCode;
    }
}
