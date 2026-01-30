package com.doozzonsa.policyissue.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PolicyIssueControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;

        // 데이터 초기화
        jdbcTemplate.update("DELETE FROM policy_issue");
    }

    @Nested
    @DisplayName("/api/v1/policy-issues GET 악용 이슈 조회 테스트")
    class ReadAllPolicyIssues {

        @Test
        @DisplayName("악용 이슈 목록 조회 시 200 OK를 응답한다")
        void readAll1() {
            // given
            LocalDate today = LocalDate.now();
            jdbcTemplate.update(
                    "INSERT INTO policy_issue (title, issue_type, summary, source, url, issue_date) " +
                            "VALUES (?, ?, ?, ?, ?, ?)",
                    "대형 쇼핑몰 A사, 고객 개인정보 230만건 유출",
                    "DATA_BREACH",
                    "이름, 연락처, 주소 등 개인정보가 해킹으로 유출",
                    "디지털 타임즈",
                    "https://example.com/news/1",
                    today.minusDays(1)
            );

            jdbcTemplate.update(
                    "INSERT INTO policy_issue (title, issue_type, summary, source, url, issue_date) " +
                            "VALUES (?, ?, ?, ?, ?, ?)",
                    "금융앱, 동의 없이 광고사에 정보 판매 적발",
                    "ABUSE",
                    "이용자 동의 없이 개인정보를 제3자 광고회사에 판매",
                    "파이낸셜뉴스",
                    "https://example.com/news/2",
                    today.minusDays(2)
            );

            // when & then
            RestAssured
                    .given().log().all()
                    .contentType(ContentType.JSON)
                    .when().get("/api/v1/policy-issues")
                    .then().log().all()
                    .statusCode(200)
                    .body("status", equalTo(200))
                    .body("message", equalTo("악용 이슈 조회 성공"))
                    .body("data.policyIssues", hasSize(2))
                    .body("data.policyIssues[0].title", equalTo("대형 쇼핑몰 A사, 고객 개인정보 230만건 유출"))
                    .body("data.policyIssues[0].issueType", equalTo("정보 유출"))
                    .body("data.policyIssues[0].summary", equalTo("이름, 연락처, 주소 등 개인정보가 해킹으로 유출"))
                    .body("data.policyIssues[0].source", equalTo("디지털 타임즈"))
                    .body("data.policyIssues[0].url", equalTo("https://example.com/news/1"))
                    .body("data.policyIssues[1].title", equalTo("금융앱, 동의 없이 광고사에 정보 판매 적발"))
                    .body("data.policyIssues[1].issueType", equalTo("약관 악용"))
                    .body("data.statistics.totalCount", equalTo(2))
                    .body("data.statistics.thisMonthCount", equalTo(2))
                    .body("data.statistics.dataBreachCount", greaterThan(0))
                    .body("data.statistics.abuseCount", greaterThan(0))
                    .body("data.thisWeekSummary", org.hamcrest.Matchers.containsString("이번 주"));
        }

        @Test
        @DisplayName("이슈가 없을 때 빈 배열을 반환한다")
        void readAll2() {
            // given - 데이터가 없는 상태

            // when & then
            RestAssured
                    .given().log().all()
                    .contentType(ContentType.JSON)
                    .when().get("/api/v1/policy-issues")
                    .then().log().all()
                    .statusCode(200)
                    .body("status", equalTo(200))
                    .body("message", equalTo("악용 이슈 조회 성공"))
                    .body("data.policyIssues", hasSize(0))
                    .body("data.statistics.totalCount", equalTo(0))
                    .body("data.statistics.thisMonthCount", equalTo(0))
                    .body("data.statistics.dataBreachCount", equalTo(0))
                    .body("data.statistics.abuseCount", equalTo(0));
        }

        @Test
        @DisplayName("최신 이슈가 먼저 조회된다 (issue_date 내림차순)")
        void readAll3() {
            // given
            LocalDate today = LocalDate.now();
            jdbcTemplate.update(
                    "INSERT INTO policy_issue (title, issue_type, summary, source, url, issue_date) " +
                            "VALUES (?, ?, ?, ?, ?, ?)",
                    "오래된 이슈",
                    "DATA_BREACH",
                    "오래된 이슈입니다",
                    "뉴스",
                    "https://example.com/old",
                    today.minusDays(10)
            );

            jdbcTemplate.update(
                    "INSERT INTO policy_issue (title, issue_type, summary, source, url, issue_date) " +
                            "VALUES (?, ?, ?, ?, ?, ?)",
                    "최신 이슈",
                    "ABUSE",
                    "최신 이슈입니다",
                    "뉴스",
                    "https://example.com/new",
                    today.minusDays(1)
            );

            // when & then
            RestAssured
                    .given().log().all()
                    .contentType(ContentType.JSON)
                    .when().get("/api/v1/policy-issues")
                    .then().log().all()
                    .statusCode(200)
                    .body("data.policyIssues[0].title", equalTo("최신 이슈"))
                    .body("data.policyIssues[1].title", equalTo("오래된 이슈"));
        }
    }
}
