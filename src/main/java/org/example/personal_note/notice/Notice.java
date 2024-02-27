package org.example.personal_note.notice;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity // 해당 클래스를 엔티티로 지정합니다.
@Table // 해당 클래스를 테이블로 지정합니다.
@Getter // Lombok 어노테이션으로, 자동으로 Getter 메서드를 생성합니다.
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 매개변수가 없는 protected 생성자를 생성합니다.
@EntityListeners(AuditingEntityListener.class) // Auditing 기능을 사용하여 생성일과 수정일을 자동으로 관리합니다.
public class Notice {

	@Id // 해당 필드를 엔티티의 기본 키로 지정합니다.
	@GeneratedValue // 자동으로 값이 생성되도록 설정합니다.
	private Long id; // 고유한 식별자인 id입니다.

	private String title; // 공지사항의 제목을 저장하는 필드입니다.

	@Lob // 대용량 데이터를 저장하기 위한 어노테이션입니다.
	private String content; // 공지사항의 내용을 저장하는 필드입니다.

	@CreatedDate // 엔티티가 생성될 때 자동으로 생성일을 기록합니다.
	private LocalDateTime createdAt; // 공지사항이 생성된 일시를 저장하는 필드입니다.

	@LastModifiedDate // 엔티티가 수정될 때 자동으로 최종 수정일을 기록합니다.
	private LocalDateTime updatedAt; // 공지사항이 최종 수정된 일시를 저장하는 필드입니다.

	// 생성자입니다. title과 content를 매개변수로 받아 초기화합니다.
	public Notice(
		String title,
		String content
	) {
		this.title = title;
		this.content = content;
	}
}