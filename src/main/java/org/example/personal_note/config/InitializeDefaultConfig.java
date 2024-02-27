package org.example.personal_note.config;

import org.example.personal_note.note.NoteService;
import org.example.personal_note.notice.NoticeService;
import org.example.personal_note.user.User;
import org.example.personal_note.user.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import lombok.RequiredArgsConstructor;

@Configuration // 빈 설정을 위한 어노테이션
@RequiredArgsConstructor // 생성자 주입을 위한 어노테이션
@Profile(value = "!test") // test 프로파일이 아닐 때만 실행
public class InitializeDefaultConfig {

	private final UserService userService; // 사용자 서비스
	private final NoteService noteService; // 노트 서비스
	private final NoticeService noticeService; // 공지사항 서비스

	// 기본 사용자 초기화를 위한 빈 설정
	@Bean
	public void initializeDefaultUser() {
		// 기본 사용자 생성
		User user = userService.signup("user", "user");
		// 사용자의 노트 생성
		noteService.saveNote(user, "테스트", "테스트입니다.");
		noteService.saveNote(user, "테스트2", "테스트2입니다.");
		noteService.saveNote(user, "테스트3", "테스트3입니다.");
		noteService.saveNote(user, "여름 여행계획", "여름 여행계획 작성중...");
	}

	// 기본 관리자 초기화를 위한 빈 설정
	@Bean
	public void initializeDefaultAdmin(){
		// 기본 관리자 생성
		userService.signupAdmin("admin", "admin");
		// 공지사항 생성
		noticeService.saveNotice("환영합니다.", "환영합니다 여러분");
		noticeService.saveNotice("노트 작성 방법 공지", "1. 회원가입\n2. 로그인\n3. 노트 작성\n4. 저장\n* 본인 외에는 게시글을 볼 수 없습니다.");
	}
}
