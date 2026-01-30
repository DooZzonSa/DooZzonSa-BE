package com.doozzonsa.enterprise.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum IndustryType {
    FINANCE("금융 / 결제 / 핀테크"),
    ECOMMERCE("전자상거래 / 쇼핑"),
    PLATFORM("포털 / 소셜 / 콘텐츠 플랫폼"),
    CLOUD_SAAS("클라우드 / B2B SaaS"),
    MANUFACTURING("제조 / 하드웨어"),
    TELECOM("통신"),
    TRAVEL("여행 / 숙박"),
    DELIVERY("배달 / O2O"),
    MOBILITY("자동차 / 모빌리티"),
    MEDIA("미디어 / 광고"),
    GENERAL_IT("기타 IT / 개발자 플랫폼");

    private final String description;
}
