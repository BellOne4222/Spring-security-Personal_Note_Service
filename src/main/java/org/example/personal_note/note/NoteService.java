package org.example.personal_note.note;

import java.util.List;

import org.example.personal_note.user.User;
import org.example.personal_note.user.UserNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

// 노트 관련 비즈니스 로직을 처리하는 서비스 클래스입니다.
@Service // 스프링 서비스로 등록됨을 나타내는 어노테이션입니다.
@Transactional // 메서드 실행 시 트랜잭션 처리를 위해 사용되는 어노테이션입니다.
// Lombok의 RequiredArgsConstructor 어노테이션으로, final 필드에 대한 생성자를 자동으로 생성합니다.
@RequiredArgsConstructor
public class NoteService {

	// NoteRepository 의존성 주입을 위한 필드
	private final NoteRepository noteRepository;

	// 사용자가 소유한 노트 목록을 조회하는 메서드입니다.
	@Transactional(readOnly = true) // 읽기 전용 트랜잭션으로 설정합니다.
	public List<Note> findByUser(User user) {

		// 사용자가 null이면 예외를 발생시킵니다.
		if (user == null) {
			throw new UserNotFoundException();
		}
		// 사용자가 관리자인 경우 모든 노트를 조회합니다.
		if (user.isAdmin()) {
			return noteRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
		}

		// 사용자가 일반 사용자인 경우 해당 사용자의 노트 목록을 최신순으로 조회합니다.
		return noteRepository.findByUserOrderByIdDesc(user);
	}

	// 새로운 노트를 저장하는 메서드입니다.
	public Note saveNote(User user, String title, String content) {
		// 사용자가 null이면 예외를 발생시킵니다.
		if (user == null) {
			throw new UserNotFoundException();
		}
		// 새로운 노트를 저장하고 반환합니다.
		return noteRepository.save(new Note(title, content, user));
	}

	// 사용자가 소유한 특정 ID의 노트를 삭제하는 메서드입니다.
	public void deleteNote(User user, Long noteId) {
		// 사용자가 null이면 예외를 발생시킵니다.
		if (user == null) {
			throw new UserNotFoundException();
		}
		// 사용자가 소유한 특정 ID의 노트를 조회합니다.
		Note note = noteRepository.findByIdAndUser(noteId, user);
		// 노트가 존재하면 삭제합니다.
		if (note != null) {
			noteRepository.delete(note);
		}
	}
}