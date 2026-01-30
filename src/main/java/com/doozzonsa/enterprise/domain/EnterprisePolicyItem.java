package com.doozzonsa.enterprise.domain;

import com.doozzonsa.policyitem.domain.PolicyItem;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@Entity
@Table(name = "enterprise_policy_item",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_enterprise_policy_item",
                columnNames = {"enterprise_id", "policy_item_id"}
        ))
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EnterprisePolicyItem {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enterprise_id", nullable = false, unique = false, foreignKey = @ForeignKey(name = "fk_enterprise_policy_item__enterprise"))
    private Enterprise enterprise;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_item_id", nullable = false, unique = false, foreignKey = @ForeignKey(name = "fk_enterprise_policy_item__policy_item"))
    private PolicyItem policyItem;

    public static EnterprisePolicyItem create(
        final Enterprise enterprise,
        final PolicyItem policyItem
    ) {
        return EnterprisePolicyItem.builder()
            .enterprise(enterprise)
            .policyItem(policyItem)
            .build();
    }
}
