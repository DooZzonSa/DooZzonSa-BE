package com.doozzonsa.enterprise.repository;

import com.doozzonsa.enterprise.domain.EnterprisePolicyItem;
import com.doozzonsa.policyitem.domain.PolicyItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnterprisePolicyItemRepository extends JpaRepository<EnterprisePolicyItem, Long> {

    int countByPolicyItem(final PolicyItem policyItem);
}
