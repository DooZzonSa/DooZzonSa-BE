package com.doozzonsa.policyitem.controller;

import com.doozzonsa.global.response.dto.SuccessResponse;
import com.doozzonsa.policyitem.controller.dto.PolicyAnalysisRequest;
import com.doozzonsa.policyitem.controller.dto.PolicyAnalysisResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "PolicyItem", description = "약관 분석 API")
public interface PolicyItemApi {

	@Operation(
		summary = "약관 분석 API",
		description = "개인정보 처리방침(약관) 텍스트를 입력받아 개인정보 항목 키워드와 주의사항을 분석합니다."
	)
	@ApiResponses({
		@ApiResponse(
			responseCode = "200",
			description = "약관 분석 성공",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = SuccessResponse.class),
				examples = @ExampleObject(
					name = "success",
					summary = "성공 예시",
					value = """
                                    {
                                      "status": 200,
                                      "message": "약관 분석이 성공하였습니다.",
                                      "data": {
                                        "agreements": [
                                          { "name": "비밀번호", "averageCollectionRate": 88 },
                                          { "name": "위치정보", "averageCollectionRate": 25 },
                                          { "name": "기기식별자", "averageCollectionRate": 13 }
                                        ],
                                        "cautions": [
                                          { "content": "서비스 이용 과정에서 IP주소, 쿠키·식별자, 서비스이용기록, 기기정보, 위치정보가 자동 수집될 수 있음." },
                                          { "content": "만 14세 미만의 경우 법정대리인 정보가 추가 수집될 수 있음." },
                                          { "content": "통계/연구/공익 기록 보존 목적에 가명처리된 정보가 활용될 수 있음." }
                                        ]
                                      }
                                    }
                                    """
				)
			)
		),
		@ApiResponse(
			responseCode = "400",
			description = "요청 본문이 올바르지 않음",
			content = @Content(
				mediaType = "application/json",
				examples = @ExampleObject(
					name = "bad_request",
					summary = "400 예시",
					value = """
                                    {
                                      "status": 400,
                                      "message": "요청 본문이 올바르지 않습니다.",
                                      "detail": "JSON parse error: ..."
                                    }
                                    """
				)
			)
		),
		@ApiResponse(
			responseCode = "500",
			description = "서버 오류",
			content = @Content(mediaType = "application/json")
		)
	})
	ResponseEntity<SuccessResponse<PolicyAnalysisResponse>> analysisPolicy(
		@RequestBody(
			required = true,
			description = "분석할 약관 원문",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = PolicyAnalysisRequest.class),
				examples = @ExampleObject(
					name = "request",
					summary = "요청 예시",
					value = """
                                    {
                                      "context": "여기에 개인정보처리방침 원문을 넣으세요..."
                                    }
                                    """
				)
			)
		)
		@jakarta.validation.Valid
		@org.springframework.web.bind.annotation.RequestBody
		PolicyAnalysisRequest request
	);
}