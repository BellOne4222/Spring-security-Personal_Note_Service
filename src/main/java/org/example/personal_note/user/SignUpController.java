package org.example.personal_note.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;


@Controller // 컨트롤러를 나타내는 클래스입니다.
@RequiredArgsConstructor // Lombok의 RequiredArgsConstructor 어노테이션으로, final 필드에 대한 생성자를 자동으로 생성합니다.
@RequestMapping("/signup") // "/signup" 경로에 매핑되는 SignUpController 클래스입니다.
public class SignUpController {

	// UserService 의존성 주입을 위한 필드
	private final UserService userService;

	// GET 요청에 대한 핸들러 메서드입니다.
	@GetMapping
	public String signup() {
		return "signup"; // "signup" 뷰를 반환합니다.
	}

	// POST 요청에 대한 핸들러 메서드입니다.
	@PostMapping
	public String signup(
		@ModelAttribute UserRegisterDto userDto // 사용자 등록 정보를 받아오는 DTO 객체
	) {
		// UserService를 통해 사용자를 등록합니다.
		userService.signup(userDto.getUsername(), userDto.getPassword());
		// 로그인 페이지로 리다이렉트합니다.
		return "redirect:login";
	}
}
