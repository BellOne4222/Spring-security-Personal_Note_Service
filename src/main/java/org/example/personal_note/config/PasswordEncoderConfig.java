package org.example.personal_note.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderConfig {

	// PasswordEncoder 빈을 생성하는 메서드입니다.
	// PasswordEncoderFactories 클래스의 createDelegatingPasswordEncoder 메서드를 호출하여 PasswordEncoder 인스턴스를 반환합니다.
	// 이 메서드는 기본적으로 DelegatingPasswordEncoder를 생성하며, 여러 개의 PasswordEncoder를 지원하고 사용자가 선택한 알고리즘에 따라 해당 알고리즘을 사용하여 암호화 및 인증을 수행합니다.
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}