package com.doozzonsa.policyissue.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.doozzonsa.policyissue.domain.IssueType;
import com.doozzonsa.policyissue.domain.PolicyIssue;
import com.doozzonsa.policyissue.repository.PolicyIssueRepository;
import com.doozzonsa.policyissue.service.dto.PolicyIssueDto;
import com.doozzonsa.policyissue.service.dto.PolicyIssuesResponse;
import com.doozzonsa.policyissue.service.dto.StatisticsDto;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PolicyIssueServiceTest {

    @InjectMocks
    private PolicyIssueService policyIssueService;

    @Mock
    private PolicyIssueRepository policyIssueRepository;

    @DisplayName("전체 이슈 조회 테스트")
    @Nested
    class ReadAll {

        @DisplayName("전체 이슈 목록과 통계를 조회할 수 있다")
        @Test
        void readAll1() {
            // given
            LocalDate now = LocalDate.now();

            PolicyIssue issue1 = new PolicyIssue(
                    1L,
                    "쇼핑몰 A사, 고객 개인정보 230만건 유출",
                    IssueType.DATA_BREACH,
                    "이름, 연락처, 주소 등 개인정보가 해킹으로 유출",
                    "디지털 타임즈",
                    "https://example.com/news/1",
                    now
            );

            PolicyIssue issue2 = new PolicyIssue(
                    2L,
                    "금융앱, 동의 없이 광고사에 정보 판매 적발",
                    IssueType.ABUSE,
                    "이용자 동의 없이 개인정보를 제3자 광고회사에 판매",
                    "파이낸셜뉴스",
                    "https://example.com/news/2",
                    now
            );

            List<PolicyIssue> mockIssues = List.of(issue1, issue2);

            given(policyIssueRepository.findAllByOrderByIssueDateDesc()).willReturn(mockIssues);

            // when
            PolicyIssuesResponse result = policyIssueService.readAll();

            // then
            assertThat(result).isNotNull();

            // 통계 검증
            StatisticsDto statistics = result.statistics();
            assertThat(statistics.totalCount()).isEqualTo(2);
            assertThat(statistics.thisMonthCount()).isEqualTo(2);
            assertThat(statistics.dataBreachCount()).isEqualTo(1);
            assertThat(statistics.abuseCount()).isEqualTo(1);

            // 이슈 목록 검증
            List<PolicyIssueDto> policyIssues = result.policyIssues();
            assertThat(policyIssues).hasSize(2);

            PolicyIssueDto firstIssue = policyIssues.get(0);
            assertThat(firstIssue.title()).isEqualTo("쇼핑몰 A사, 고객 개인정보 230만건 유출");
            assertThat(firstIssue.issueType()).isEqualTo("정보 유출");
            assertThat(firstIssue.summary()).isEqualTo("이름, 연락처, 주소 등 개인정보가 해킹으로 유출");
            assertThat(firstIssue.source()).isEqualTo("디지털 타임즈");
            assertThat(firstIssue.url()).isEqualTo("https://example.com/news/1");

            PolicyIssueDto secondIssue = policyIssues.get(1);
            assertThat(secondIssue.title()).isEqualTo("금융앱, 동의 없이 광고사에 정보 판매 적발");
            assertThat(secondIssue.issueType()).isEqualTo("약관 악용");

            // 이번 주 요약 검증
            assertThat(result.thisWeekSummary()).isNotNull();
            assertThat(result.thisWeekSummary()).contains("쇼핑몰");
        }

        @DisplayName("이슈가 없을 때 빈 목록을 반환한다")
        @Test
        void readAll2() {
            // given
            given(policyIssueRepository.findAllByOrderByIssueDateDesc()).willReturn(List.of());

            // when
            PolicyIssuesResponse result = policyIssueService.readAll();

            // then
            assertThat(result).isNotNull();
            assertThat(result.policyIssues()).isEmpty();
            assertThat(result.statistics().totalCount()).isZero();
            assertThat(result.statistics().thisMonthCount()).isZero();
            assertThat(result.statistics().dataBreachCount()).isZero();
            assertThat(result.statistics().abuseCount()).isZero();
        }
    }
}
