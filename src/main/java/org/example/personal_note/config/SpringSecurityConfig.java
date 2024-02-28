package org.example.personal_note.config;

import org.example.personal_note.user.User;
import org.example.personal_note.user.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;


import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	private final UserService userService;

	// HTTP 보안 설정을 구성하는 메서드입니다.
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Basic Authentication을 비활성화합니다.
		http.httpBasic().disable();
		// Basic Authentication을 활성화합니다.
		// http.httpBasic();
		// CSRF 보호를 활성화합니다.
		http.csrf();
		// Remember-Me를 활성화합니다.
		http.rememberMe();
		// URL에 대한 인가 규칙을 설정합니다.
		http.authorizeRequests()
			// "/"와 "/home", "/signup" 경로는 모든 사용자에게 허용합니다.
			.antMatchers("/", "/home", "/signup").permitAll()
			// "/note" 페이지는 USER 역할을 가진 사용자에게만 허용합니다.
			.antMatchers("/note").hasRole("USER")
			// "/admin" 페이지는 ADMIN 역할을 가진 사용자에게만 허용합니다.
			.antMatchers("/admin").hasRole("ADMIN")
			// "/notice" 페이지에 대한 POST 요청은 ADMIN 역할을 가진 사용자에게만 허용합니다.
			.antMatchers(HttpMethod.POST, "/notice").hasRole("ADMIN")
			// "/notice" 페이지에 대한 DELETE 요청은 ADMIN 역할을 가진 사용자에게만 허용합니다.
			.antMatchers(HttpMethod.DELETE, "/notice").hasRole("ADMIN")
			// 그 외의 모든 요청은 인증된 사용자에게만 허용합니다.
			.anyRequest().authenticated();
		// 로그인 페이지 및 성공 후 이동할 URL을 설정합니다.
		http.formLogin()
			.loginPage("/login")
			.defaultSuccessUrl("/")
			.permitAll(); // 모든 사용자에게 로그인 페이지를 허용합니다.
		// 로그아웃을 처리하는 URL 및 로그아웃 후 이동할 URL을 설정합니다.
		http.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/");
	}

	// Spring Security가 정적 리소스를 무시하도록 설정합니다.
	@Override
	public void configure(WebSecurity web) {
		web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
	}

	// 사용자 정보를 로드하는 UserDetailsService 빈을 설정합니다.
	@Bean
	@Override
	public UserDetailsService userDetailsService() {
		return username -> {
			User user = userService.findByUsername(username);
			if (user == null) {
				throw new UsernameNotFoundException(username);
			}
			return user;
		};
	}
}
