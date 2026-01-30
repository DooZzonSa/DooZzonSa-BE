package com.doozzonsa.policyissue.repository;

import com.doozzonsa.policyissue.domain.IssueType;
import com.doozzonsa.policyissue.domain.PolicyIssue;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyIssueRepository extends JpaRepository<PolicyIssue, Long> {

    int countByIssueType(IssueType issueType);

    int countByIssueDateBetween(LocalDate startDate, LocalDate endDate);

    List<PolicyIssue> findAllByOrderByIssueDateDesc();
}
