package com.ssafy.tomorrowdiray_backend.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ssafy.tomorrowdiray_backend.global.permission.AuthInterceptor;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

	private final AuthInterceptor authInterceptor;

	// // 구현의 편의를 위해 일단 인터셉터 적용 x
	// @Override
	// public void addInterceptors(InterceptorRegistry registry) {
	// 	registry.addInterceptor(authInterceptor)
	// 		.addPathPatterns("/**") // 모든 요청 경로에 대해 인터셉터 적용
	// 		.excludePathPatterns("/static/**", "/login", "/error"); // 제외할 경로 설정;
	// }
}
