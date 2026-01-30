package com.doozzonsa.policyitem.controller;

import com.doozzonsa.global.response.dto.SuccessResponse;
import com.doozzonsa.policyitem.service.dto.PolicyItemStatisticsDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "PolicyItem", description = "개인정보 수집 항목 API")
public interface PolicyItemControllerDocs {

    @Operation(
            summary = "수집 통계 조회 API",
            description = "수집한 개인정보 항목에 대한 통계를 조회한다"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "성공 예시",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "success",
                                    summary = "성공적으로 개인정보 수집 항목 통계 조회",
                                    value = """
                                            {
                                            	"status": 200,
                                            	"message": "수집 통계 조회 성공",
                                            	"data": {
                                            	  "policyItems": [
                                            	    {
                                            	      "name": "실명",
                                            	      "averageCollectionRate": 18,
                                            	      "riskLevel": "높음"
                                            	    },
                                            	    {
                                            	      "name": "전화번호",
                                            	      "averageCollectionRate": 40,
                                            	      "riskLevel": "중간"
                                            	    },
                                            	    {
                                            	      "name": "성별",
                                            	      "averageCollectionRate": 60,
                                            	      "riskLevel": "낮음"
                                            	    }
                                            	  ],
                                            		"riskLevels": [
                                            			{
                                            				"level": "낮음",
                                            				"policyItems": [
                                            			    {
                                            			      "name": "실명"
                                            			    },
                                            			    {
                                            			      "name": "전화번호"
                                            			    },
                                            			    {
                                            			      "name": "기기 정보"
                                            			    }
                                            				]
                                            			},
                                            			{
                                            				"level": "중간",
                                            				"policyItems": [
                                            			    {
                                            			      "name": "주소지역정보"
                                            			    },
                                            			    {
                                            			      "name": "생년월일"
                                            			    },
                                            			    {
                                            			      "name": "성별"
                                            			    }
                                            				]
                                            			},
                                            			{
                                            				"level": "높음",
                                            				"policyItems": [
                                            			    {
                                            			      "name": "건강정보"
                                            			    },
                                            			    {
                                            			      "name": "결제 정보"
                                            			    },
                                            			    {
                                            			      "name": "위치 정보"
                                            			    }
                                            				]
                                            			}
                                            		]
                                            	}
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<SuccessResponse<PolicyItemStatisticsDto>> readStatistics();
}
