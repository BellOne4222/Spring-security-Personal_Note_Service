package org.example.personal_note.user;

// 이미 등록된 사용자 예외를 나타내는 클래스입니다.
public class AlreadyRegisteredUserException extends RuntimeException {

	// 메시지를 포함하는 생성자
	public AlreadyRegisteredUserException(String message) {
		super(message); // 부모 클래스의 생성자를 호출하여 예외 메시지를 설정합니다.
	}

	// 기본 메시지를 사용하는 생성자
	public AlreadyRegisteredUserException() {
		super("이미 등록된 유저입니다."); // 기본 메시지를 설정하여 부모 클래스의 생성자를 호출합니다.
	}
}
