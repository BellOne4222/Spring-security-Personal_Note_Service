package org.example.personal_note.notice;

import org.springframework.data.jpa.repository.JpaRepository;

// 공지사항 엔티티를 데이터베이스에서 조회, 저장, 수정, 삭제하는 데 사용되는 리포지토리 인터페이스입니다.
public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
