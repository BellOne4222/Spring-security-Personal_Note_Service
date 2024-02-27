package org.example.personal_note.notice;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service // 해당 클래스가 비즈니스 로직을 담당하는 서비스 클래스임을 나타냅니다.
@Transactional // 메서드 실행 시 트랜잭션 처리를 자동으로 수행합니다.
@RequiredArgsConstructor // Lombok 어노테이션으로 필드 주입을 위한 생성자를 자동으로 생성합니다.
public class NoticeService {

	private final NoticeRepository noticeRepository; // Notice 엔티티와 상호작용하기 위한 Repository

	// 모든 공지사항을 최신순으로 조회하는 메서드
	@Transactional(readOnly = true) // 읽기 전용 트랜잭션 설정
	public List<Notice> findAll() {
		return noticeRepository.findAll(Sort.by(Sort.Direction.DESC, "id")); // 최신순으로 공지사항 조회
	}

	// 공지사항을 저장하는 메서드
	public Notice saveNotice(String title, String content) {
		return noticeRepository.save(new Notice(title, content)); // 새로운 공지사항 저장
	}

	// 특정 공지사항을 삭제하는 메서드
	public void deleteNotice(Long id) {
		noticeRepository.findById(id).ifPresent(noticeRepository::delete); // 공지사항이 존재하면 삭제
	}
}