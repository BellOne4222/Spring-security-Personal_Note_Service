package org.example.personal_note.admin;

import java.util.List;

import org.example.personal_note.note.Note;
import org.example.personal_note.user.User;
import org.springframework.security.core.Authentication;
import org.example.personal_note.note.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

// admin인 경우에만 노트를 조회 할 수 있게 구현
@Controller // 스프링 MVC 컨트롤러로 설정됩니다.
// Lombok의 RequiredArgsConstructor 어노테이션으로, final 필드에 대한 생성자를 자동으로 생성합니다.
@RequiredArgsConstructor
// "/admin" 경로에 매핑되는 AdminController 클래스입니다.
@RequestMapping("/admin")
public class AdminController {

	// NoteService 의존성 주입을 위한 필드
	private final NoteService noteService;

	// GET 요청에 대한 핸들러 메서드입니다.
	@GetMapping
	public String getNoteForAdmin(Authentication authentication, Model model) {
		// 현재 사용자 정보를 가져옵니다.
		User user = (User) authentication.getPrincipal();
		// 현재 사용자의 모든 노트를 조회합니다.
		List<Note> notes = noteService.findByUser(user);
		// 뷰로 전달할 모델에 노트 목록을 추가합니다.
		model.addAttribute("notes", notes);
		// 관리자 페이지로 이동합니다.
		return "admin/index";
	}
}
