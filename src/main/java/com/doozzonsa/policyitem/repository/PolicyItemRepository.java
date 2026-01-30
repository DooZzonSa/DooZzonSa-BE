package com.doozzonsa.policyitem.repository;

import com.doozzonsa.policyitem.domain.PolicyItem;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyItemRepository extends JpaRepository<PolicyItem, Long> {

    Optional<PolicyItem> findByName(String name);
}
