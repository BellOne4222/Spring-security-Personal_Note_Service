package org.example.personal_note.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Getter 및 Setter 메서드가 자동으로 생성되는 클래스입니다.
@Getter // Lombok 어노테이션으로, 자동으로 Getter 메서드를 생성합니다.
@Setter // Lombok 어노테이션으로, 자동으로 Setter 메서드를 생성합니다.
// 파라미터 없는 기본 생성자를 생성하는 Lombok 어노테이션입니다. 상속됨으로써 하위 클래스에서만 사용될 수 있습니다.
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// 모든 필드를 매개변수로 받는 생성자를 생성하는 Lombok 어노테이션입니다. 상속됨으로써 하위 클래스에서만 사용될 수 있습니다.
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class UserRegisterDto {

	// 사용자명 필드
	private String username;
	// 비밀번호 필드
	private String password;
}
