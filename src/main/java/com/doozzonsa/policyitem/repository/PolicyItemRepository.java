package com.doozzonsa.policyitem.repository;

import com.doozzonsa.policyitem.domain.PolicyItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyItemRepository extends JpaRepository<PolicyItem, Long> {
}
