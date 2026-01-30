package com.doozzonsa.policyitem.controller.dto;

import java.util.List;

public record PolicyAnalysisResult(
	List<String> policyItems,
	List<String> warnings
) {
}
