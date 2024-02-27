package org.example.personal_note.user;

import org.springframework.data.jpa.repository.JpaRepository;

// 사용자 정보를 저장하는 레포지토리입니다.
public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String name); // 사용자명으로 사용자를 찾는 메서드
}
