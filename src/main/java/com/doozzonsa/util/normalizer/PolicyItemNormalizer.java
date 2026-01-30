package com.doozzonsa.util.normalizer;

import java.util.HashMap;
import java.util.Map;

public final class PolicyItemNormalizer {
	private PolicyItemNormalizer() {}

	private static final Map<String, String> ALIAS_TO_CANONICAL = new HashMap<>();

	static {
		ALIAS_TO_CANONICAL.put("접속아이피정보", "IP주소");
		ALIAS_TO_CANONICAL.put("접속IP정보", "IP주소");

		ALIAS_TO_CANONICAL.put("쿠키·식별자", "쿠키");
		ALIAS_TO_CANONICAL.put("쿠키정보", "쿠키");
		ALIAS_TO_CANONICAL.put("쿠키·추적식별자", "쿠키");

		ALIAS_TO_CANONICAL.put("아이디", "로그인ID");
		ALIAS_TO_CANONICAL.put("로그인아이디", "로그인ID");
		ALIAS_TO_CANONICAL.put("사용자명", "로그인ID");

		ALIAS_TO_CANONICAL.put("휴대전화번호", "휴대폰번호");

		ALIAS_TO_CANONICAL.put("단말기정보", "기기정보");
		ALIAS_TO_CANONICAL.put("기기식별정보", "기기식별자");

		ALIAS_TO_CANONICAL.put("GPS위치정보", "위치정보");
		ALIAS_TO_CANONICAL.put("개인위치정보", "위치정보");

		ALIAS_TO_CANONICAL.put("프로필이미지", "프로필사진");
		ALIAS_TO_CANONICAL.put("이미지정보", "이미지");
		ALIAS_TO_CANONICAL.put("영상정보", "영상");
		ALIAS_TO_CANONICAL.put("사진", "이미지");
		ALIAS_TO_CANONICAL.put("이미지·영상", "이미지/영상");

		ALIAS_TO_CANONICAL.put("접속기록", "접속로그");
		ALIAS_TO_CANONICAL.put("서비스접속로그", "접속로그");
		ALIAS_TO_CANONICAL.put("웹사이트방문이력", "방문기록");
		ALIAS_TO_CANONICAL.put("방문이력", "방문기록");
	}

	public static String normalize(String raw) {
		String s = raw.trim();

		// 공백/특수문자 흔들리는 거 최소 정리
		s = s.replace("·", "·"); // 유지
		s = s.replaceAll("\\s+", " ");

		return ALIAS_TO_CANONICAL.getOrDefault(s, s);
	}
}