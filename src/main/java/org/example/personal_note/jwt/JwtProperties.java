package org.example.personal_note.jwt;

/**
 * JWT 기본 설정값
 */
public class JwtProperties {
	// JWT 토큰의 만료 시간 설정 (10분)
	public static final int EXPIRATION_TIME = 600000; // 10분
	// JWT 토큰을 저장할 쿠키의 이름
	public static final String COOKIE_NAME = "JWT-AUTHENTICATION";
}