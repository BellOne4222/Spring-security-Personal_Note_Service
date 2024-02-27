package org.example.personal_note.user;

import java.util.Collection;
import java.util.Collections;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity // 사용자 엔티티를 나타내는 클래스로, 데이터베이스의 사용자 테이블과 매핑됩니다.
@Table // 해당 엔티티를 매핑할 테이블 이름을 지정합니다.
@Getter // Lombok 어노테이션으로, 자동으로 필드에 대한 Getter 메서드를 생성합니다.
@NoArgsConstructor // Lombok 어노테이션으로, 파라미터가 없는 기본 생성자를 생성합니다.
public class User implements UserDetails {

	// id 필드
	@GeneratedValue
	@Id
	private Long id;

	// 사용자명 필드
	private String username;

	// 비밀번호 필드
	private String password;

	// 권한 필드
	private String authority;

	// User 생성자
	public User(
		String username,
		String password,
		String authority
	) {
		this.username = username;
		this.password = password;
		this.authority = authority;
	}

	// 사용자의 권한을 반환하는 메서드
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities(){
		return Collections.singleton((GrantedAuthority) () -> authority);
	}

	// 사용자가 관리자인지 확인하는 메서드
	public Boolean isAdmin() {
		return authority.equals("ROLE_ADMIN");
	}

	// 사용자의 계정이 만료되지 않았는지 확인하는 메서드
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	// 사용자의 계정이 잠겨있지 않은지 확인하는 메서드
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	// 사용자의 자격 증명이 만료되지 않았는지 확인하는 메서드
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	// 사용자가 활성화되어 있는지 확인하는 메서드
	@Override
	public boolean isEnabled() {
		return true;
	}
}