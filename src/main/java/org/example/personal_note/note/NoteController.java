package org.example.personal_note.note;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.example.personal_note.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import lombok.RequiredArgsConstructor;

// 노트 관련 요청을 처리하는 컨트롤러 클래스입니다.
@Controller
// Lombok의 RequiredArgsConstructor 어노테이션으로, final 필드에 대한 생성자를 자동으로 생성합니다.
@RequiredArgsConstructor
// "/note" 경로에 매핑되는 NoteController 클래스입니다.
@RequestMapping("/note")
public class NoteController {

	// NoteService 의존성 주입을 위한 필드
	private final NoteService noteService;

	// GET 요청에 대한 핸들러 메서드입니다.
	@GetMapping
	public String getNote(Authentication authentication, Model model) {
		// 현재 사용자 정보를 가져옵니다.
		User user = (User) authentication.getPrincipal();
		// 현재 사용자의 모든 노트를 조회합니다.
		List<Note> notes = noteService.findByUser(user);
		// 뷰로 전달할 모델에 노트 목록을 추가합니다.
		model.addAttribute("notes", notes);
		// 노트 목록을 보여주는 뷰로 이동합니다.
		return "note/index";
	}

	// POST 요청에 대한 핸들러 메서드입니다.
	@PostMapping
	public String saveNote(Authentication authentication, @ModelAttribute NoteRegisterDto noteDto) {
		// 현재 사용자 정보를 가져옵니다.
		User user = (User) authentication.getPrincipal();
		// 사용자가 작성한 새로운 노트를 저장합니다.
		noteService.saveNote(user, noteDto.getTitle(), noteDto.getContent());
		// 노트 목록 페이지로 리다이렉트합니다.
		return "redirect:note";
	}

	// DELETE 요청에 대한 핸들러 메서드입니다.
	@DeleteMapping
	public String deleteNote(Authentication authentication, @RequestParam Long id) {
		// 현재 사용자 정보를 가져옵니다.
		User user = (User) authentication.getPrincipal();
		// 사용자가 선택한 노트를 삭제합니다.
		noteService.deleteNote(user, id);
		// 노트 목록 페이지로 리다이렉트합니다.
		return "redirect:note";
	}
}
