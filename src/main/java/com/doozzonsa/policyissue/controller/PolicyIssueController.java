package com.doozzonsa.policyissue.controller;

import com.doozzonsa.global.response.dto.SuccessResponse;
import com.doozzonsa.policyissue.service.PolicyIssueService;
import com.doozzonsa.policyissue.service.dto.PolicyIssuesResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/policy-issues")
@Tag(name = "PolicyIssue", description = "약관 이슈 API")
public class PolicyIssueController {

    private final PolicyIssueService policyIssueService;

    @Operation(
            summary = "약관 이슈 조회 API",
            description = "약관 이슈를 조회한다"
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
                                    summary = "성공적으로 약관 이슈 조회",
                                    value = """
                                            {
                                            	"status": 200,
                                            	"message": "악용 이슈 조회 성공",
                                            	"data": {
                                            		"thisWeekSummary": "이번 주 가장 주의할 소식은 A쇼핑몰 고객정보 유출이에요. 이름, 연락처, 주소가 빠져나갔어요. B금융앱은 동의 없이 광고사에 정보를 팔다 적발됐고요. 가입된 서비스 약관, 한 번 점검해보시는 게 좋겠어요.",
                                            	  "policyIssues": [
                                            	    {
                                            	      "title": "대형 쇼핑몰 A사, 고객 개인정보 230만건 유출",
                                            	      "summary": "이름, 연락처, 주소 등 개인정보가 해킹으로 유출. 2차 피해 주의 필요",
                                            	      "source": "디지털 타임즈",
                                            	      "issueType": "정보 유출",
                                            	      "url": "https://~~"
                                            	    },
                                            	    {
                                            	      "title": "대형 쇼핑몰 A사, 고객 개인정보 230만건 유출",
                                            	      "summary": "이름, 연락처, 주소 등 개인정보가 해킹으로 유출. 2차 피해 주의 필요",
                                            	      "source": "디지털 타임즈",
                                            	      "issueType": "약관 악용",
                                            	      "url": "https://~~"
                                            	    }
                                            	  ],
                                            		"statistics": {
                                            			"totalCount": 127,
                                            			"thisMonthCount": 12,
                                            			"dataBreachCount": 45,
                                            			"abuseCount": 52
                                            		}
                                            	}
                                            }
                                            """
                            )
                    )
            )
    })
    @GetMapping
    public ResponseEntity<SuccessResponse<PolicyIssuesResponse>> readAll() {
        PolicyIssuesResponse data = policyIssueService.readAll();

        SuccessResponse<PolicyIssuesResponse> response = new SuccessResponse<>(
                200,
                "악용 이슈 조회 성공",
                data
        );

        return ResponseEntity.ok(response);
    }
}
