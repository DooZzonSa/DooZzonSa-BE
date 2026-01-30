package com.doozzonsa.enterprise.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.doozzonsa.enterprise.domain.Enterprise;

public interface EnterpriseRepository extends JpaRepository<Enterprise, Long> {

	Optional<Enterprise> findByName(String name);

	List<Enterprise> findAll();
}
