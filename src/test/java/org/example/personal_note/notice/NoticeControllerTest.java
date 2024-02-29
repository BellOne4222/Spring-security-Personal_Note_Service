package org.example.personal_note.notice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest // 스프링 부트 애플리케이션 컨텍스트를 로드하여 테스트 환경을 설정합니다.
@Transactional // 각 테스트 메소드가 실행된 후 롤백되어 테스트 간 데이터 오염을 방지합니다.
class NoticeControllerTest {

	@Autowired
	private NoticeRepository noticeRepository; // NoticeRepository를 주입합니다.
	private MockMvc mockMvc; // MockMvc 객체를 선언합니다.

	@BeforeEach
	public void setUp(@Autowired WebApplicationContext applicationContext) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext)
			.apply(springSecurity()) // Spring Security를 적용합니다.
			.alwaysDo(print()) // 모든 요청과 응답을 콘솔에 출력합니다.
			.build();
	}

	// 인증 없이 공지사항을 조회하는 경우를 테스트합니다.
	@Test
	void getNotice_인증없음() throws Exception {
		mockMvc.perform(get("/notice"))
			.andExpect(redirectedUrlPattern("**/login")) // 로그인 페이지로 리다이렉트되는지 확인합니다.
			.andExpect(status().is3xxRedirection()); // HTTP 상태 코드가 리다이렉션인지 확인합니다.
	}

	// 인증된 사용자가 공지사항을 조회하는 경우를 테스트합니다.
	@Test
	@WithMockUser // 인증된 가짜 사용자를 사용하여 테스트합니다.
	void getNotice_인증있음() throws Exception {
		mockMvc.perform(get("/notice"))
			.andExpect(status().isOk()) // HTTP 상태 코드가 200인지 확인합니다.
			.andExpect(view().name("notice/index")); // 뷰 이름이 "notice/index"인지 확인합니다.
	}

	// 인증 없이 공지사항을 게시하는 경우를 테스트합니다.
	@Test
	void postNotice_인증없음() throws Exception {
		mockMvc.perform(
			post("/notice")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("title", "제목")
				.param("content", "내용")
		).andExpect(status().isForbidden()); // 접근 거부
	}

	// 유저 권한으로 공지사항을 게시하는 경우를 테스트합니다.
	@Test
	@WithMockUser(roles = {"USER"}, username = "admin", password = "admin")
	void postNotice_유저인증있음() throws Exception {
		mockMvc.perform(
			post("/notice").with(csrf()) // CSRF 토큰을 함께 전송합니다.
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("title", "제목")
				.param("content", "내용")
		).andExpect(status().isForbidden()); // 접근 거부
	}

	// 어드민 권한으로 공지사항을 게시하는 경우를 테스트합니다.
	@Test
	@WithMockUser(roles = {"ADMIN"}, username = "admin", password = "admin")
	void postNotice_어드민인증있음() throws Exception {
		mockMvc.perform(
				post("/notice").with(csrf()) // CSRF 토큰을 함께 전송합니다.
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
					.param("title", "제목")
					.param("content", "내용")
			).andExpect(redirectedUrl("notice")) // 공지사항 페이지로 리다이렉트되는지 확인합니다.
			.andExpect(status().is3xxRedirection()); // HTTP 상태 코드가 리다이렉션인지 확인합니다.
	}

	// 인증 없이 공지사항을 삭제하는 경우를 테스트합니다.
	@Test
	void deleteNotice_인증없음() throws Exception {
		Notice notice = noticeRepository.save(new Notice("제목", "내용"));
		mockMvc.perform(
			delete("/notice?id=" + notice.getId())
		).andExpect(status().isForbidden()); // 접근 거부
	}

	// 유저 권한으로 공지사항을 삭제하는 경우를 테스트합니다.
	@Test
	@WithMockUser(roles = {"USER"}, username = "admin", password = "admin")
	void deleteNotice_유저인증있음() throws Exception {
		Notice notice = noticeRepository.save(new Notice("제목", "내용"));
		mockMvc.perform(
			delete("/notice?id=" + notice.getId()).with(csrf()) // CSRF 토큰을 함께 전송합니다.
		).andExpect(status().isForbidden()); // 접근 거부
	}

	// 어드민 권한으로 공지사항을 삭제하는 경우를 테스트합니다.
	@Test
	@WithMockUser(roles = {"ADMIN"}, username = "admin", password = "admin")
	void deleteNotice_어드민인증있음() throws Exception {
		Notice notice = noticeRepository.save(new Notice("제목", "내용"));
		mockMvc.perform(
				delete("/notice?id=" + notice.getId()).with(csrf()) // CSRF 토큰을 함께 전송합니다.
			).andExpect(redirectedUrl("notice")) // 공지사항 페이지로 리다이렉트되는지 확인합니다.
			.andExpect(status().is3xxRedirection()); // HTTP 상태 코드가 리다이렉션인지 확인합니다.
	}
}