package org.example.personal_note.note;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.example.personal_note.user.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import lombok.Getter;
import lombok.NoArgsConstructor;

// 데이터베이스의 노트 테이블과 매핑되는 엔티티 클래스입니다.
@Entity
// 테이블을 명시하지 않으면 클래스 이름을 테이블 이름으로 사용합니다.
@Table
@Getter // Lombok 어노테이션으로, 자동으로 Getter 메서드를 생성합니다.
// 파라미터가 없는 기본 생성자를 생성하는 Lombok 어노테이션입니다. 상속됨으로써 하위 클래스에서만 사용될 수 있습니다.
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
// JPA 엔티티의 생성, 수정 날짜를 자동으로 기록하는 엔티티 리스너를 설정합니다.
@EntityListeners(AuditingEntityListener.class)
public class Note {

	// ID 필드
	@Id
	@GeneratedValue
	private Long id;

	// 제목 필드
	private String title;

	// 내용 필드
	@Lob // 대용량 데이터(LOB: Large Object)를 저장하기 위한 필드임을 나타냅니다.
	private String content;

	// 사용자와의 관계를 나타내는 필드
	@ManyToOne(fetch = FetchType.LAZY) // 다대일(N:1) 관계를 설정합니다. 지연 로딩(LAZY)으로 설정됩니다.
	@JoinColumn(name = "USER_ID") // 외래키를 지정합니다.
	private User user;

	// 생성일자를 나타내는 필드
	@CreatedDate // JPA Auditing 기능을 사용하여 엔티티가 생성될 때 자동으로 날짜를 설정합니다.
	private LocalDateTime createdAt;

	// 수정일자를 나타내는 필드
	@LastModifiedDate // JPA Auditing 기능을 사용하여 엔티티가 수정될 때 자동으로 날짜를 설정합니다.
	private LocalDateTime updatedAt;

	// Note 생성자
	public Note(
		String title,
		String content,
		User user
	) {
		this.title = title;
		this.content = content;
		this.user = user;
	}

	// 내용을 업데이트하는 메서드
	public void updateContent(
		String title,
		String content
	) {
		this.title = title;
		this.content = content;
	}
}
