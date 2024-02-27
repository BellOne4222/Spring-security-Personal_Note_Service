package org.example.personal_note.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service // 스프링에게 이 클래스가 서비스임을 알려주는 어노테이션입니다.
@RequiredArgsConstructor // Lombok 어노테이션으로, final 필드를 파라미터로 받는 생성자를 생성합니다.
// 사용자 서비스를 나타내는 클래스입니다.
public class UserService {

	private final UserRepository userRepository; // UserRepository 의존성 주입을 위한 필드
	private final PasswordEncoder passwordEncoder; // PasswordEncoder 의존성 주입을 위한 필드

	// 사용자 등록 메서드입니다.
	public User signup(String username, String password) {
		// 이미 등록된 사용자명이 있는 경우 예외를 발생시킵니다.
		if (userRepository.findByUsername(username) != null) {
			throw new AlreadyRegisteredUserException();
		}
		// 비밀번호를 암호화(인코딩)하여 사용자를 저장합니다.
		return userRepository.save(new User(username, passwordEncoder.encode(password), "ROLE_USER"));
	}

	// 관리자 등록 메서드입니다.
	public User signupAdmin(String username, String password) {
		// 이미 등록된 사용자명이 있는 경우 예외를 발생시킵니다.
		if (userRepository.findByUsername(username) != null) {
			throw new AlreadyRegisteredUserException();
		}
		// 비밀번호를 암호화(인코딩)하여 관리자를 저장합니다.
		return userRepository.save(new User(username, passwordEncoder.encode(password), "ROLE_ADMIN"));
	}

	// 사용자명으로 사용자를 찾는 메서드입니다.
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
}
