package com.doozzonsa.enterprise.repository;

import com.doozzonsa.enterprise.domain.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnterpriseRepository extends JpaRepository<Enterprise, Long> {
}
