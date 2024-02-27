package org.example.personal_note.note;

import lombok.Getter;
import lombok.Setter;

// 노트 등록에 사용되는 데이터 전송 객체(DTO) 클래스입니다.
@Getter // Lombok 어노테이션으로, Getter 메서드를 자동으로 생성합니다.
@Setter // Lombok 어노테이션으로, Setter 메서드를 자동으로 생성합니다.
public class NoteRegisterDto {
	// 제목 필드
	private String title;
	// 내용 필드
	private String content;
}
