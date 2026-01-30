package com.doozzonsa.policyitem.service;

import com.doozzonsa.enterprise.repository.EnterprisePolicyItemRepository;
import com.doozzonsa.enterprise.repository.EnterpriseRepository;
import com.doozzonsa.policyitem.domain.PolicyItem;
import com.doozzonsa.policyitem.domain.RiskLevel;
import com.doozzonsa.policyitem.repository.PolicyItemRepository;
import com.doozzonsa.policyitem.service.dto.PolicyItemDto;
import com.doozzonsa.policyitem.service.dto.PolicyItemNameDto;
import com.doozzonsa.policyitem.service.dto.PolicyItemStatisticsDto;
import com.doozzonsa.policyitem.service.dto.RiskLevelDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PolicyItemService {

    private final PolicyItemRepository policyItemRepository;
    private final EnterpriseRepository enterpriseRepository;
    private final EnterprisePolicyItemRepository enterprisePolicyItemRepository;

    public PolicyItemStatisticsDto getStatistics() {
        List<PolicyItem> policyItems = policyItemRepository.findAll();
        int enterpriseCount = (int) enterpriseRepository.count();

        List<PolicyItemDto> policyItemDtos = getPolicyItemDtos(policyItems, enterpriseCount);
        List<RiskLevelDto> riskLevelDtos = getRiskLevelDtos(policyItems);

        return new PolicyItemStatisticsDto(policyItemDtos, riskLevelDtos);
    }

    private List<PolicyItemDto> getPolicyItemDtos(final List<PolicyItem> policyItems, final int enterpriseCount) {
        return policyItems.stream()
                .map((policyItem) -> {
                    int averageCollectionRate = calculateAverageCollectionRate(policyItem, enterpriseCount);
                    return new PolicyItemDto(policyItem.getName(), averageCollectionRate, policyItem.getRiskLevel());
                }).toList();
    }

    private List<RiskLevelDto> getRiskLevelDtos(final List<PolicyItem> policyItems) {
        return RiskLevel.getValidValues().stream()
                .map(riskLevel -> {
                    List<PolicyItemNameDto> policyItemNames = policyItems.stream()
                            .filter(policyItem -> policyItem.isLevelOf(riskLevel))
                            .map(PolicyItem::getName)
                            .map(PolicyItemNameDto::new)
                            .toList();

                    return new RiskLevelDto(riskLevel, policyItemNames);
                })
                .toList();
    }

    private int calculateAverageCollectionRate(final PolicyItem policyItem, final int enterpriseCount) {
        if (enterpriseCount == 0) {
            return 0;
        }
        int policyItemCount = enterprisePolicyItemRepository.countByPolicyItem(policyItem);
        return (policyItemCount * 100) / enterpriseCount;
    }
}
