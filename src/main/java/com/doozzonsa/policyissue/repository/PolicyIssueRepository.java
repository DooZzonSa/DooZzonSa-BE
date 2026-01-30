package com.doozzonsa.policyissue.repository;

import com.doozzonsa.policyissue.domain.PolicyIssue;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyIssueRepository extends JpaRepository<PolicyIssue, Long> {

    List<PolicyIssue> findAllByOrderByIssueDateDesc();
}
