package org.example.personal_note.user;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest // 스프링 부트 애플리케이션 컨텍스트를 로드하여 테스트 환경을 설정합니다.
@Transactional // 각 테스트 메소드가 실행된 후 롤백되어 테스트 간 데이터 오염을 방지합니다.
class SignUpControllerTest {

	private MockMvc mockMvc; // MockMvc 객체를 선언합니다.

	@BeforeEach
	public void setUp(@Autowired WebApplicationContext applicationContext) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext)
			.apply(springSecurity()) // Spring Security를 적용합니다.
			.alwaysDo(print()) // 모든 요청과 응답을 콘솔에 출력합니다.
			.build();
	}

	// 회원가입을 테스트합니다.
	@Test
	void signup() throws Exception {
		mockMvc.perform(
				post("/signup").with(csrf()) // CSRF 토큰을 함께 전송합니다.
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
					.param("username", "user123") // 회원 정보를 파라미터로 전송합니다.
					.param("password", "password")
			).andExpect(redirectedUrl("login")) // 로그인 페이지로 리다이렉트되는지 확인합니다.
			.andExpect(status().is3xxRedirection()); // HTTP 상태 코드가 리다이렉션인지 확인합니다.
	}
}