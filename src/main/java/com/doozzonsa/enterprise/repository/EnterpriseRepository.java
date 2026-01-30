package com.doozzonsa.enterprise.repository;

import com.doozzonsa.enterprise.domain.Enterprise;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnterpriseRepository extends JpaRepository<Enterprise, Long> {

    Optional<Enterprise> findByName(String name);

    List<Enterprise> findAll();
}
