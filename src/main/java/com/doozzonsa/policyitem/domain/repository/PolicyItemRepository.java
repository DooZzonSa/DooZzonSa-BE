package com.doozzonsa.policyitem.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.doozzonsa.policyitem.domain.PolicyItem;

public interface PolicyItemRepository extends JpaRepository<PolicyItem, Long> {

	Optional<PolicyItem> findByName(String name);
}
