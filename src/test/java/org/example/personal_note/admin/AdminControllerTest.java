package org.example.personal_note.admin;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.example.personal_note.user.User;
import org.example.personal_note.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest // 통합 테스트를 위한 애플리케이션 컨텍스트를 로드합니다.
@ActiveProfiles(profiles = "test") //  "test" 프로파일이 활성화되도록 설정
@Transactional // 각각의 테스트 메소드 실행 후 롤백하여 데이터베이스의 상태를 변경하지 않고 테스트를 수행
class AdminControllerTest {

	@Autowired // 스프링에 의해 자동 주입되는 필드를 나타냅니다. 이 경우에는 userRepository가 자동으로 주입됩니다.
	private UserRepository userRepository;
	private MockMvc mockMvc;
	private User user;
	private User admin;

	@BeforeEach // 각 테스트 메소드가 실행되기 전에 실행되는 메소드입니다. 여기서는 테스트에 필요한 MockMvc 객체를 설정하고, 테스트용 사용자 데이터를 데이터베이스에 저장합니다.
	public void setUp(@Autowired WebApplicationContext applicationContext) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext)
			.apply(SecurityMockMvcConfigurers.springSecurity()) // Spring Security 설정 적용
			.alwaysDo(print())
			.build();
		// ROLE_USER 권한이 있는 유저 생성
		user = userRepository.save(new User("user", "user", "ROLE_USER"));
		// ROLE_ADMIN 권한이 있는 관리자 생성
		admin = userRepository.save(new User("admin", "admin", "ROLE_ADMIN"));
	}

	// 특정 URL에 대한 테스트 케이스: 인증 없이 접근하는 경우
	@Test
	void getNoteForAdmin_인증없음() throws Exception {
		mockMvc.perform(get("/admin").with(csrf())) // CSRF 토큰 추가
			.andExpect(redirectedUrlPattern("**/login")) // 로그인 페이지로 리다이렉션되는지 확인
			.andExpect(status().is3xxRedirection()); // 3xx 리다이렉션 상태코드 반환 여부 확인
	}

	// 특정 URL에 대한 테스트 케이스: 어드민 권한으로 접근하는 경우
	@Test
	void getNoteForAdmin_어드민인증있음() throws Exception {
		mockMvc.perform(get("/admin").with(csrf()).with(user(admin))) // 어드민 유저로 요청
			.andExpect(status().is2xxSuccessful()); // 2xx 성공 상태코드 반환 여부 확인
	}

	// 특정 URL에 대한 테스트 케이스: 유저 권한으로 접근하는 경우
	@Test
	void getNoteForAdmin_유저인증있음() throws Exception {
		mockMvc.perform(get("/admin").with(csrf()).with(user(user))) // 유저로 요청
			.andExpect(status().isForbidden()); // 403 Forbidden 상태코드 반환 여부 확인
	}
}