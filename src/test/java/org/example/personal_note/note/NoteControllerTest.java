package org.example.personal_note.note;

import org.example.personal_note.user.User;
import org.example.personal_note.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles(profiles = "test")
@Transactional
class NoteControllerTest {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private NoteRepository noteRepository;
	private MockMvc mockMvc;
	private User user;
	private User admin;

	@BeforeEach
	public void setUp(@Autowired WebApplicationContext applicationContext) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext)
			.apply(springSecurity())
			.alwaysDo(print())
			.build();
		// 유저와 어드민 데이터 준비
		user = userRepository.save(new User("user123", "user", "ROLE_USER"));
		admin = userRepository.save(new User("admin123", "admin", "ROLE_ADMIN"));
	}

	// 인증 없이 노트에 접근하는 경우를 테스트합니다.
	@Test
	void getNote_인증없음() throws Exception {
		mockMvc.perform(get("/note"))
			.andExpect(redirectedUrlPattern("**/login"))
			.andExpect(status().is3xxRedirection());
	}

	// 인증된 사용자가 노트에 접근하는 경우를 테스트합니다.
	@Test
	@WithUserDetails(
		value = "user123", // 사용자명
		userDetailsServiceBeanName = "userDetailsService", // UserDetailsService 구현체의 Bean
		setupBefore = TestExecutionEvent.TEST_EXECUTION // 테스트 실행 직전에 유저를 가져온다.
	)
	void getNote_인증있음() throws Exception {
		mockMvc.perform(
				get("/note")
			).andExpect(status().isOk()) // 성공적인 응답 여부 확인
			.andExpect(view().name("note/index")) // 정상적인 뷰 반환 여부 확인
			.andDo(print()); // 결과를 출력
	}

	// 인증 없이 노트를 작성하는 경우를 테스트합니다.
	@Test
	void postNote_인증없음() throws Exception {
		mockMvc.perform(
				post("/note").with(csrf())
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
					.param("title", "제목")
					.param("content", "내용")
			).andExpect(redirectedUrlPattern("**/login")) // 로그인 페이지로 리다이렉션 여부 확인
			.andExpect(status().is3xxRedirection()); // 3xx 리다이렉션 상태코드 반환 여부 확인
	}

	// 어드민 권한으로 노트를 작성하는 경우를 테스트합니다.
	@Test
	@WithUserDetails(
		value = "admin123",
		userDetailsServiceBeanName = "userDetailsService",
		setupBefore = TestExecutionEvent.TEST_EXECUTION
	)
	void postNote_어드민인증있음() throws Exception {
		mockMvc.perform(
			post("/note").with(csrf())
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("title", "제목")
				.param("content", "내용")
		).andExpect(status().isForbidden()); // 접근 거부
	}

	// 유저 권한으로 노트를 작성하는 경우를 테스트합니다.
	@Test
	@WithUserDetails(
		value = "user123",
		userDetailsServiceBeanName = "userDetailsService",
		setupBefore = TestExecutionEvent.TEST_EXECUTION
	)
	void postNote_유저인증있음() throws Exception {
		mockMvc.perform(
			post("/note").with(csrf())
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("title", "제목")
				.param("content", "내용")
		).andExpect(redirectedUrl("note")).andExpect(status().is3xxRedirection());
	}

	// 인증 없이 노트를 삭제하는 경우를 테스트합니다.
	@Test
	void deleteNote_인증없음() throws Exception {
		Note note = noteRepository.save(new Note("제목", "내용", user));
		mockMvc.perform(
				delete("/note?id=" + note.getId()).with(csrf())
			).andExpect(redirectedUrlPattern("**/login"))
			.andExpect(status().is3xxRedirection());
	}

	// 유저 권한으로 노트를 삭제하는 경우를 테스트합니다.
	@Test
	@WithUserDetails(
		value = "user123",
		userDetailsServiceBeanName = "userDetailsService",
		setupBefore = TestExecutionEvent.TEST_EXECUTION
	)
	void deleteNote_유저인증있음() throws Exception {
		Note note = noteRepository.save(new Note("제목", "내용", user));
		mockMvc.perform(
			delete("/note?id=" + note.getId()).with(csrf())
		).andExpect(redirectedUrl("note")).andExpect(status().is3xxRedirection());
	}

	// 어드민 권한으로 노트를 삭제하는 경우를 테스트합니다.
	@Test
	@WithUserDetails(
		value = "admin123",
		userDetailsServiceBeanName = "userDetailsService",
		setupBefore = TestExecutionEvent.TEST_EXECUTION
	)
	void deleteNote_어드민인증있음() throws Exception {
		Note note = noteRepository.save(new Note("제목", "내용", user));
		mockMvc.perform(
			delete("/note?id=" + note.getId()).with(csrf()).with(user(admin))
		).andExpect(status().isForbidden()); // 접근 거부
	}
}