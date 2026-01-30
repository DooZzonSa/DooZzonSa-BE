package com.doozzonsa.policyitem.exception;

import org.springframework.http.HttpStatus;

import com.doozzonsa.global.response.base.BaseCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PolicyItemSuccessCode implements BaseCode {

	/*
	200 OK
	 */
	POLICY_ANALYSIS_SUCCESS(HttpStatus.OK, "약관 분석이 성공하였습니다."),
	;

	private final HttpStatus httpStatus;
	private final String message;
}
