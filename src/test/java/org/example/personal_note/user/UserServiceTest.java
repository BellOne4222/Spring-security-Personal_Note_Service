package org.example.personal_note.user;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest // 스프링 부트 애플리케이션 컨텍스트를 로드하여 테스트 환경을 설정합니다.
@ActiveProfiles(profiles = "test") // 활성화된 프로파일이 "test"인 경우를 지정합니다.
@Transactional // 각 테스트 메소드가 실행된 후 롤백되어 테스트 간 데이터 오염을 방지합니다.
class UserServiceTest {

	@Autowired
	private UserService userService; // UserService를 주입합니다.
	@Autowired
	private UserRepository userRepository; // UserRepository를 주입합니다.

	// 회원 가입을 테스트합니다.
	@Test
	void signup() {
		// given
		String username = "user123";
		String password = "password";

		// when
		User user = userService.signup(username, password);

		// then
		then(user.getId()).isNotNull(); // id가 NotNull인지 검증
		then(user.getUsername()).isEqualTo("user123"); // 유저명이 user123인지 검증
		then(user.getPassword()).startsWith("{bcrypt}"); // 패스워드가 {bcrypt}로 시작하는지 검증
		then(user.getAuthorities()).hasSize(1); // Authorities가 1개인지 검증
		then(user.getAuthorities().stream().findFirst().get().getAuthority()).isEqualTo("ROLE_USER");
		then(user.isAdmin()).isFalse(); // 어드민 여부가 False인지 검증
		then(user.isAccountNonExpired()).isTrue();
		then(user.isAccountNonLocked()).isTrue();
		then(user.isEnabled()).isTrue();
		then(user.isCredentialsNonExpired()).isTrue();
	}

	// 어드민 회원 가입을 테스트합니다.
	@Test
	void signupAdmin() {
		// given
		String username = "admin123";
		String password = "password";

		// when
		User user = userService.signupAdmin(username, password);

		// then
		then(user.getId()).isNotNull();
		then(user.getUsername()).isEqualTo("admin123");
		then(user.getPassword()).startsWith("{bcrypt}");
		then(user.getAuthorities()).hasSize(1);
		then(user.getAuthorities().stream().findFirst().get().getAuthority()).isEqualTo("ROLE_ADMIN");
		then(user.isAdmin()).isTrue();
		then(user.isAccountNonExpired()).isTrue();
		then(user.isAccountNonLocked()).isTrue();
		then(user.isEnabled()).isTrue();
		then(user.isCredentialsNonExpired()).isTrue();
	}

	// 유저명으로 유저를 조회하는 테스트를 수행합니다.
	@Test
	void findByUsername() {
		// given
		userRepository.save(new User("user123", "password", "ROLE_USER"));

		// when
		User user = userService.findByUsername("user123");

		// then
		then(user.getId()).isNotNull();
	}
}