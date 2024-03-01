package org.example.personal_note.jwt;

import org.example.personal_note.user.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * JWT를 이용한 로그인 인증
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;

	/**
	 * JwtAuthenticationFilter 생성자
	 * @param authenticationManager AuthenticationManager 객체
	 */
	public JwtAuthenticationFilter(AuthenticationManager authenticationManager) { // AuthenticationManager 객체를 주입받는 생성자
		super(authenticationManager); // 부모 클래스의 생성자 호출
		this.authenticationManager = authenticationManager; // AuthenticationManager 객체 저장
	}

	/**
	 * 로그인 인증 시도
	 * @param request HttpServletRequest 객체
	 * @param response HttpServletResponse 객체
	 * @return Authentication 객체
	 * @throws AuthenticationException 인증 예외 발생 시
	 */
	@Override
	public Authentication attemptAuthentication( // 로그인 인증 시도
		HttpServletRequest request, // HTTP 요청
		HttpServletResponse response // HTTP 응답
	) throws AuthenticationException { // 인증 예외 발생 시
		// 로그인할 때 입력한 username과 password를 가지고 authenticationToken를 생성한다.
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
			request.getParameter("username"),
			request.getParameter("password"),
			new ArrayList<>()
		);
		return authenticationManager.authenticate(authenticationToken);
	}

	/**
	 * 인증에 성공했을 때 사용
	 * JWT Token을 생성해서 쿠키에 넣는다.
	 * @param request HttpServletRequest 객체
	 * @param response HttpServletResponse 객체
	 * @param chain FilterChain 객체
	 * @param authResult Authentication 객체
	 * @throws IOException 입출력 예외 발생 시
	 */
	@Override
	protected void successfulAuthentication(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain chain,
		Authentication authResult
	) throws IOException {
		User user = (User) authResult.getPrincipal();
		String token = JwtUtils.createToken(user);
		// 쿠키 생성
		Cookie cookie = new Cookie(JwtProperties.COOKIE_NAME, token);
		cookie.setMaxAge(JwtProperties.EXPIRATION_TIME); // 쿠키의 만료시간 설정
		cookie.setPath("/");
		response.addCookie(cookie);
		response.sendRedirect("/");
	}

	/**
	 * 인증에 실패했을 때 사용
	 * @param request HttpServletRequest 객체
	 * @param response HttpServletResponse 객체
	 * @param failed AuthenticationException 객체
	 * @throws IOException 입출력 예외 발생 시
	 */
	@Override
	protected void unsuccessfulAuthentication(
		HttpServletRequest request,
		HttpServletResponse response,
		AuthenticationException failed
	) throws IOException {
		response.sendRedirect("/login");
	}
}
