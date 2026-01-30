package com.doozzonsa.policyitem.controller.dto;

import java.util.List;

public record PolicyAnalysisResponse(
	List<AgreementsRate> agreements,
	List<Content> cautions
) {
	public record AgreementsRate(
		String name,
		int averageCollectionRate
	) { }

	public record Content(
		String content
	){}
}
