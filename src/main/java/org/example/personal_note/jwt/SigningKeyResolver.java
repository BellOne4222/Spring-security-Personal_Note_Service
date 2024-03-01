package org.example.personal_note.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.SigningKeyResolverAdapter;

import java.security.Key;

/**
 * JwsHeader를 통해 Signature 검증에 필요한 Key를 가져오는 코드를 구현합니다.
 */
public class SigningKeyResolver extends SigningKeyResolverAdapter {
	public static SigningKeyResolver instance = new SigningKeyResolver();

	/**
	 * JwsHeader로부터 서명 키를 가져오는 메서드를 재정의합니다.
	 * @param jwsHeader JWS 헤더
	 * @param claims 토큰 클레임
	 * @return 서명 키
	 */
	@Override
	public Key resolveSigningKey(JwsHeader jwsHeader, Claims claims) {
		String kid = jwsHeader.getKeyId(); // 헤더에서 키 식별자(kid)를 가져옵니다.
		if (kid == null)
			return null;
		return JwtKey.getKey(kid); // 주어진 kid에 대응하는 서명 키를 가져옵니다.
	}
}
