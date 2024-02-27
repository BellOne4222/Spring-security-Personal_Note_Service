package org.example.personal_note.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration //  스프링 컨텍스트에서 빈으로 등록되는 설정 클래스
@EnableJpaAuditing // JPA 감사(Auditing) 기능을 활성화  JPA 엔티티의 생성일자(createdDate)와 수정일자(lastModifiedDate)를 자동으로 관리
public class JpaAuditorConfig {
}
