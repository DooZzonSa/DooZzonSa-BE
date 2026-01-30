package com.doozzonsa.policyitem.controller.dto;

import jakarta.validation.constraints.NotNull;

public record PolicyAnalysisRequest(
	@NotNull
	String context
) {
}
