package com.doozzonsa.policyitem.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.doozzonsa.enterprise.repository.EnterprisePolicyItemRepository;
import com.doozzonsa.enterprise.repository.EnterpriseRepository;
import com.doozzonsa.policyitem.domain.PolicyItem;
import com.doozzonsa.policyitem.domain.RiskLevel;
import com.doozzonsa.policyitem.repository.PolicyItemRepository;
import com.doozzonsa.policyitem.service.dto.PolicyItemDto;
import com.doozzonsa.policyitem.service.dto.PolicyItemStatisticsDto;
import com.doozzonsa.policyitem.service.dto.RiskLevelDto;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PolicyItemServiceTest {

    @InjectMocks
    private PolicyItemService policyItemService;

    @Mock
    private PolicyItemRepository policyItemRepository;

    @Mock
    private EnterpriseRepository enterpriseRepository;

    @Mock
    private EnterprisePolicyItemRepository enterprisePolicyItemRepository;

    @DisplayName("수집 통계 조회 테스트")
    @Nested
    class GetStatistics {

        @DisplayName("수집 통계를 조회할 수 있다")
        @Test
        void getStatistics1() {
            // given
            PolicyItem item1 = new PolicyItem(1L, "실명", RiskLevel.HIGH);
            PolicyItem item2 = new PolicyItem(2L, "전화번호", RiskLevel.MID);
            PolicyItem item3 = new PolicyItem(3L, "성별", RiskLevel.LOW);
            List<PolicyItem> policyItems = List.of(item1, item2, item3);

            given(policyItemRepository.findAll()).willReturn(policyItems);
            given(enterpriseRepository.count()).willReturn(2L);
            given(enterprisePolicyItemRepository.countByPolicyItem(item1)).willReturn(1);
            given(enterprisePolicyItemRepository.countByPolicyItem(item2)).willReturn(2);
            given(enterprisePolicyItemRepository.countByPolicyItem(item3)).willReturn(2);

            // when
            PolicyItemStatisticsDto result = policyItemService.getStatistics();

            // then
            assertThat(result).isNotNull();

            List<PolicyItemDto> policyItemDtos = result.policyItems();
            assertThat(policyItemDtos).hasSize(3);

            PolicyItemDto firstItem = policyItemDtos.get(0);
            assertThat(firstItem.name()).isEqualTo("실명");
            assertThat(firstItem.averageCollectionRate()).isEqualTo(50);
            assertThat(firstItem.riskLevel()).isEqualTo("높음");

            PolicyItemDto secondItem = policyItemDtos.get(1);
            assertThat(secondItem.name()).isEqualTo("전화번호");
            assertThat(secondItem.averageCollectionRate()).isEqualTo(100);
            assertThat(secondItem.riskLevel()).isEqualTo("중간");

            PolicyItemDto thirdItem = policyItemDtos.get(2);
            assertThat(thirdItem.name()).isEqualTo("성별");
            assertThat(thirdItem.averageCollectionRate()).isEqualTo(100);
            assertThat(thirdItem.riskLevel()).isEqualTo("낮음");

            List<RiskLevelDto> riskLevels = result.riskLevels();
            assertThat(riskLevels).hasSize(3);

            RiskLevelDto high = riskLevels.stream()
                    .filter(level -> level.level().equals("높음"))
                    .findFirst()
                    .orElseThrow();
            assertThat(high.policyItems()).extracting("name").containsExactly("실명");

            RiskLevelDto mid = riskLevels.stream()
                    .filter(level -> level.level().equals("중간"))
                    .findFirst()
                    .orElseThrow();
            assertThat(mid.policyItems()).extracting("name").containsExactly("전화번호");

            RiskLevelDto low = riskLevels.stream()
                    .filter(level -> level.level().equals("낮음"))
                    .findFirst()
                    .orElseThrow();
            assertThat(low.policyItems()).extracting("name").containsExactly("성별");
        }
    }
}
