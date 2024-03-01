package org.example.personal_note.jwt;

import java.security.Key;
import java.util.Date;

import org.example.personal_note.user.User;
import org.springframework.data.util.Pair;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;

public class JwtUtils {
	/**
	 * 토큰에서 username 찾기
	 *
	 * @param token 토큰
	 * @return username
	 */
	public static String getUsername(String token) {
		// jwtToken에서 username을 찾습니다.
		return Jwts.parserBuilder()  // JWT 파서 빌더 생성
			.setSigningKeyResolver(SigningKeyResolver.instance)  // 서명 키 설정
			.build()  // JWT 파서 빌드
			.parseClaimsJws(token)  // 토큰 파싱
			.getBody()  // 토큰의 본문(Claims) 얻기
			.getSubject(); // subject 반환
	}

	/**
	 * user로 토큰 생성
	 * HEADER : alg, kid
	 * PAYLOAD : sub, iat, exp
	 * SIGNATURE : JwtKey.getRandomKey로 구한 Secret Key로 HS512 해시
	 *
	 * @param user 유저
	 * @return jwt token
	 */
	public static String createToken(User user) {
		Claims claims = Jwts.claims().setSubject(user.getUsername()); // subject 설정
		Date now = new Date(); // 현재 시간
		Pair<String, Key> key = JwtKey.getRandomKey();  // 랜덤한 키 가져오기
		// JWT Token 생성
		return Jwts.builder()
			.setClaims(claims) // 정보 저장
			.setIssuedAt(now) // 토큰 발행 시간 정보 설정
			.setExpiration(new Date(now.getTime() + JwtProperties.EXPIRATION_TIME)) // 토큰 만료 시간 설정
			.setHeaderParam(JwsHeader.KEY_ID, key.getFirst()) // kid 설정
			.signWith(key.getSecond()) // 서명 설정
			.compact(); // 토큰 생성
	}
}
