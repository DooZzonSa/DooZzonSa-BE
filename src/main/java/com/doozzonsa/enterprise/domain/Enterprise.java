package com.doozzonsa.enterprise.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@Entity
@Table(name = "enterprise")
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Enterprise {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "scale", nullable = false, length = 255)
    private Scale scale;

    @Column(name = "country", nullable = false, length = 255)
    private String country;

    @Enumerated(EnumType.STRING)
    @Column(name = "industry_type", nullable = false, length = 255)
    private IndustryType industryType;

    public static Enterprise create(
        final String name,
        final Scale scale,
        final String country,
        final IndustryType industryType
    ) {
        return Enterprise.builder()
            .name(name)
            .scale(scale)
            .country(country)
            .industryType(industryType)
            .build();
    }
}
