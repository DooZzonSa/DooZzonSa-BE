package com.doozzonsa.enterprise.repository;

import com.doozzonsa.enterprise.domain.EnterprisePolicyItem;
import com.doozzonsa.policyitem.domain.PolicyItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface EnterprisePolicyItemRepository extends JpaRepository<EnterprisePolicyItem, Long> {

    int countByPolicyItem(final PolicyItem policyItem);

    @Modifying
    @Query(value = """
            INSERT IGNORE INTO enterprise_policy_item(enterprise_id, policy_item_id)
            VALUES (:enterpriseId, :policyItemId)
            """, nativeQuery = true)
    void insertIgnore(Long enterpriseId, Long policyItemId);

    default void saveAllIgnoreDuplicates(List<EnterprisePolicyItem> list) {
        for (EnterprisePolicyItem e : list) {
            insertIgnore(e.getEnterprise().getId(), e.getPolicyItem().getId());
        }
    }

    @Query("""
            SELECT epi.enterprise.id, epi.policyItem.name
            FROM EnterprisePolicyItem  epi
            WHERE epi.enterprise.id in :enterpriseIds
            """)
    List<Object[]> findEnterpriseIdAndPolicyItemNameByEnterpriseIds(List<Long> enterpriseIds);
}
