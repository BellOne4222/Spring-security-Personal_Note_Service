package org.example.personal_note.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // 스프링 컨텍스트에서 빈으로 등록되는 설정 클래스를 나타냅니다.
public class MvcConfig implements WebMvcConfigurer {

	// 스프링 컨텍스트에서 빈으로 등록되는 설정 클래스를 나타냅니다.
	// WebMvcConfigurer 인터페이스를 구현하여 MVC 구성을 커스터마이징합니다.

	// ViewControllerRegistry를 사용하여 뷰 컨트롤러를 등록하는 메서드입니다.
	// 이 메서드를 재정의하여 원하는 경로에 대한 뷰를 설정할 수 있습니다.
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		// "/home" 경로에 대한 요청이 들어오면 "index" 뷰를 반환하는 뷰 컨트롤러를 등록합니다.
		registry.addViewController("/home").setViewName("index");
		// "/" 경로에 대한 요청이 들어오면 "index" 뷰를 반환하는 뷰 컨트롤러를 등록합니다.
		registry.addViewController("/").setViewName("index");
		// "/login" 경로에 대한 요청이 들어오면 "login" 뷰를 반환하는 뷰 컨트롤러를 등록합니다.
		registry.addViewController("/login").setViewName("login");
	}
}