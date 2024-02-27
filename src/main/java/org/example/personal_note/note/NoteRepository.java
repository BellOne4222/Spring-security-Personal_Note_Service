package org.example.personal_note.note;

import java.util.List;

import org.example.personal_note.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

// 노트 엔티티를 데이터베이스에서 조회, 저장, 수정, 삭제하는 데 사용되는 리포지토리 인터페이스입니다.
public interface NoteRepository extends JpaRepository<Note, Long> {

	// 사용자의 노트 목록을 최신 순으로 조회하는 메서드입니다.
	List<Note> findByUserOrderByIdDesc(User user);

	// 사용자가 소유한 특정 ID의 노트를 조회하는 메서드입니다.
	Note findByIdAndUser(Long id, User user);
}
