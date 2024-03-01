package org.example.personal_note.jwt;

import org.example.personal_note.user.User;
import org.example.personal_note.user.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * JWT를 이용한 인증
 */
public class JwtAuthorizationFilter extends OncePerRequestFilter {

	private final UserRepository userRepository; // UserRepository 객체

	/**
	 * JwtAuthorizationFilter 생성자
	 * @param userRepository UserRepository 객체
	 */
	public JwtAuthorizationFilter(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * HTTP 요청 필터링 메서드
	 * @param request HttpServletRequest 객체
	 * @param response HttpServletResponse 객체
	 * @param chain FilterChain 객체
	 * @throws IOException 입출력 예외 발생 시
	 * @throws ServletException 서블릿 예외 발생 시
	 */
	@Override
	protected void doFilterInternal(
		HttpServletRequest request, // HTTP 요청
		HttpServletResponse response, // HTTP 응답
		FilterChain chain // 필터 체인
	) throws IOException, ServletException {
		String token = null; // JWT 토큰
		try {
			// cookie 에서 JWT token을 가져옵니다.
			token = Arrays.stream(request.getCookies()) // 쿠키 배열을 Stream으로 변환합니다.
				.filter(cookie -> cookie.getName().equals(JwtProperties.COOKIE_NAME)).findFirst() // JWT 쿠키를 찾습니다.
				.map(Cookie::getValue) // JWT 쿠키의 값을 가져옵니다.
				.orElse(null); // JWT 쿠키가 없으면 NULL
		} catch (Exception ignored) { // 쿠키에서 JWT 토큰을 가져오는데 실패하면 NULL
		}
		if (token != null) { // JWT 토큰이 있다면
			try {
				// JWT 토큰으로 User를 찾아서 UsernamePasswordAuthenticationToken를 만들어서 SecurityContextHolder에 넣습니다.
				Authentication authentication = getUsernamePasswordAuthenticationToken(token);
				SecurityContextHolder.getContext().setAuthentication(authentication);
				// JWT 토큰이 유효하면 다음 필터로 넘깁니다.
			} catch (Exception e) { // JWT 토큰이 유효하지 않은 경우
				Cookie cookie = new Cookie(JwtProperties.COOKIE_NAME, null); // 쿠키를 삭제합니다.
				cookie.setMaxAge(0); // 쿠키의 유효시간을 0으로 설정합니다.
				response.addCookie(cookie); // 쿠키를 추가합니다.
			}
		}
		chain.doFilter(request, response);
	}

	/**
	 * JWT 토큰으로 User를 찾아서 UsernamePasswordAuthenticationToken를 만들어서 반환한다.
	 * User가 없다면 null
	 * @param token JWT 토큰
	 * @return Authentication 객체
	 */
	private Authentication getUsernamePasswordAuthenticationToken(String token) {
		String userName = JwtUtils.getUsername(token);
		if (userName != null) {
			User user = userRepository.findByUsername(userName); // 유저를 유저명으로 찾습니다.
			return new UsernamePasswordAuthenticationToken(
				user, // principal
				null,
				user.getAuthorities()
			);
		}
		return null; // 유저가 없으면 NULL
	}
}
