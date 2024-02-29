package org.example.personal_note.note;

import static org.junit.jupiter.api.Assertions.*;

import org.example.personal_note.user.User;
import org.example.personal_note.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest // 스프링 부트 애플리케이션 컨텍스트를 로드하여 테스트 환경을 설정합니다.
@ActiveProfiles(profiles = "test") // "test" 프로파일을 활성화하여 테스트 시 환경설정을 구성합니다.
@Transactional // 각 테스트 메소드가 실행된 후 롤백되어 테스트 간 데이터 오염을 방지합니다.
class NoteServiceTest {

	@Autowired
	private NoteService noteService; // 테스트할 노트 서비스를 주입합니다.
	@Autowired
	private UserRepository userRepository; // 사용자 레포지토리를 주입합니다.
	@Autowired
	private NoteRepository noteRepository; // 노트 레포지토리를 주입합니다.

	// 사용자가 게시한 노트를 조회하는 경우를 테스트합니다.
	@Test
	void findByUser_유저가_게시물조회() {
		// given
		User user = userRepository.save(new User("username", "password", "ROLE_USER"));
		noteRepository.save(new Note("title1", "content1", user));
		noteRepository.save(new Note("title2", "content2", user));
		// when
		List<Note> notes = noteService.findByUser(user);
		// then
		then(notes.size()).isEqualTo(2); // 사용자에 의해 생성된 노트 수를 확인
		Note note1 = notes.get(0);
		Note note2 = notes.get(1);

		// note1 = title2
		then(note1.getUser().getUsername()).isEqualTo("username");
		then(note1.getTitle()).isEqualTo("title2"); // 최신 노트가 먼저 조회되어야 함
		then(note1.getContent()).isEqualTo("content2");
		// note2 = title1
		then(note2.getUser().getUsername()).isEqualTo("username");
		then(note2.getTitle()).isEqualTo("title1");
		then(note2.getContent()).isEqualTo("content1");
	}

	// 어드민이 모든 사용자의 게시물을 조회하는 경우를 테스트합니다.
	@Test
	void findByUser_어드민이_조회() {
		// given
		User admin = userRepository.save(new User("admin", "password", "ROLE_ADMIN"));
		User user1 = userRepository.save(new User("username", "password", "ROLE_USER"));
		User user2 = userRepository.save(new User("username2", "password", "ROLE_USER"));
		noteRepository.save(new Note("title1", "content1", user1));
		noteRepository.save(new Note("title2", "content2", user1));
		noteRepository.save(new Note("title3", "content3", user2));
		// when
		List<Note> notes = noteService.findByUser(admin);
		// then
		then(notes.size()).isEqualTo(3); // 모든 노트를 조회해야 함
		Note note1 = notes.get(0);
		Note note2 = notes.get(1);
		Note note3 = notes.get(2);

		// note1 = title3
		then(note1.getUser().getUsername()).isEqualTo("username2");
		then(note1.getTitle()).isEqualTo("title3"); // 최신 노트가 먼저 조회되어야 함
		then(note1.getContent()).isEqualTo("content3");
		// note2 = title2
		then(note2.getUser().getUsername()).isEqualTo("username");
		then(note2.getTitle()).isEqualTo("title2");
		then(note2.getContent()).isEqualTo("content2");
		// note3 = title1
		then(note3.getUser().getUsername()).isEqualTo("username");
		then(note3.getTitle()).isEqualTo("title1");
		then(note3.getContent()).isEqualTo("content1");
	}

	// 노트를 저장하는 경우를 테스트합니다.
	@Test
	void saveNote() {
		// given
		User user = userRepository.save(new User("username", "password", "ROLE_USER"));
		// when
		noteService.saveNote(user, "title1", "content1");
		// then
		then(noteRepository.count()).isOne(); // 노트가 저장되었는지 확인
	}

	// 노트를 삭제하는 경우를 테스트합니다.
	@Test
	void deleteNote() {
		User user = userRepository.save(new User("username", "password", "ROLE_USER"));
		Note note = noteRepository.save(new Note("title1", "content1", user));
		noteService.deleteNote(user, note.getId());
		// then
		then(noteRepository.count()).isZero(); // 노트가 삭제되었는지 확인
	}
}