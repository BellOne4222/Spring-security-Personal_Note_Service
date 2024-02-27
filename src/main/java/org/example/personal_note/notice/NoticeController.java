package org.example.personal_note.notice;

import java.util.List;

import org.example.personal_note.note.NoteRegisterDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;


@Controller // 해당 클래스가 컨트롤러임을 나타냅니다.
@RequiredArgsConstructor // Lombok 어노테이션으로 필드 주입을 위한 생성자를 자동으로 생성합니다.
@RequestMapping("/notice") // 이 컨트롤러의 매핑된 URL을 지정합니다.
public class NoticeController {

	private final NoticeService noticeService; // NoticeService 의존성을 주입받습니다.

	// GET 요청을 처리하여 공지사항 목록을 조회하는 메서드입니다.
	@GetMapping
	public String getNotice(Model model) {
		// 공지사항 서비스를 사용하여 모든 공지사항을 조회합니다.
		List<Notice> notices = noticeService.findAll();
		// 조회된 공지사항을 모델에 추가합니다.
		model.addAttribute("notices", notices);
		// 공지사항 목록 페이지로 이동합니다.
		return "notice/index";
	}

	// POST 요청을 처리하여 공지사항을 등록하는 메서드입니다.
	@PostMapping
	public String postNotice(@ModelAttribute NoteRegisterDto noteDto) {
		// 공지사항 서비스를 사용하여 주어진 DTO로부터 공지사항을 등록합니다.
		noticeService.saveNotice(noteDto.getTitle(), noteDto.getContent());
		// 공지사항 목록 페이지로 리다이렉트합니다.
		return "redirect:notice";
	}

	// DELETE 요청을 처리하여 공지사항을 삭제하는 메서드입니다.
	@DeleteMapping
	public String deleteNotice(@RequestParam Long id) {
		// 공지사항 서비스를 사용하여 주어진 ID에 해당하는 공지사항을 삭제합니다.
		noticeService.deleteNotice(id);
		// 공지사항 목록 페이지로 리다이렉트합니다.
		return "redirect:notice";
	}
}
