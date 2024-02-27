package org.example.personal_note.user;

// 사용자를 찾을 수 없는 경우 발생하는 예외를 나타내는 클래스입니다.
public class UserNotFoundException extends RuntimeException {

	// 메시지를 포함하는 생성자
	public UserNotFoundException(String message) {
		super(message); // 부모 클래스의 생성자를 호출하여 예외 메시지를 설정합니다.
	}

	// 기본 메시지를 사용하는 생성자
	public UserNotFoundException() {
		super("유저를 찾을 수 없습니다."); // 기본 메시지를 설정하여 부모 클래스의 생성자를 호출합니다.
	}
}
