package com.doozzonsa.policyitem.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;

import com.doozzonsa.policyitem.service.PolicyItemService;
import com.doozzonsa.policyitem.service.dto.PolicyItemDto;
import com.doozzonsa.policyitem.service.dto.PolicyItemNameDto;
import com.doozzonsa.policyitem.service.dto.PolicyItemStatisticsDto;
import com.doozzonsa.policyitem.service.dto.RiskLevelDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PolicyItemControllerTest {

    @LocalServerPort
    private int port;

    @MockBean
    private PolicyItemService policyItemService;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Nested
    @DisplayName("/api/v1/statistics GET 수집 통계 조회 테스트")
    class ReadStatistics {

        @Test
        @DisplayName("수집 통계 조회 시 200 OK를 응답한다")
        void readStatistics1() {
            // given
            List<PolicyItemDto> policyItems = List.of(
                    new PolicyItemDto("실명", 18, "높음"),
                    new PolicyItemDto("전화번호", 40, "중간"),
                    new PolicyItemDto("성별", 60, "낮음")
            );

            List<RiskLevelDto> riskLevels = List.of(
                    new RiskLevelDto("낮음", List.of(
                            new PolicyItemNameDto("실명"),
                            new PolicyItemNameDto("전화번호"),
                            new PolicyItemNameDto("기기 정보")
                    )),
                    new RiskLevelDto("중간", List.of(
                            new PolicyItemNameDto("주소지역정보"),
                            new PolicyItemNameDto("생년월일"),
                            new PolicyItemNameDto("성별")
                    )),
                    new RiskLevelDto("높음", List.of(
                            new PolicyItemNameDto("건강정보"),
                            new PolicyItemNameDto("결제 정보"),
                            new PolicyItemNameDto("위치 정보")
                    ))
            );

            PolicyItemStatisticsDto response = new PolicyItemStatisticsDto(policyItems, riskLevels);
            given(policyItemService.getStatistics()).willReturn(response);

            // when & then
            RestAssured
                    .given().log().all()
                    .contentType(ContentType.JSON)
                    .when().get("/api/v1/statistics")
                    .then().log().all()
                    .statusCode(200)
                    .body("status", equalTo(200))
                    .body("message", equalTo("수집 통계 조회 성공"))
                    .body("data.policyItems", hasSize(3))
                    .body("data.policyItems[0].name", equalTo("실명"))
                    .body("data.policyItems[0].averageCollectionRate", equalTo(18))
                    .body("data.policyItems[0].riskLevel", equalTo("높음"))
                    .body("data.riskLevels", hasSize(3))
                    .body("data.riskLevels[0].level", equalTo("낮음"))
                    .body("data.riskLevels[0].policyItems", hasSize(3));
        }
    }
}
