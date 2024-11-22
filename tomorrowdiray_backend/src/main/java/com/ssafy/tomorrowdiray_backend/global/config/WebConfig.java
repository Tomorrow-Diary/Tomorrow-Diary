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

	 @Override
	 public void addInterceptors(InterceptorRegistry registry) {
		 registry.addInterceptor(authInterceptor)
				 .addPathPatterns("/**") // 모든 요청 경로에 대해 인터셉터 적용
				 .excludePathPatterns(
						 "/api/v1/user/login/**",
						 "/api/v1/user/signup",
						 "/swagger-resources/**",
						 "/swagger-ui/**",
						 "/v3/api-docs/**",
						 "/callback"
				 ); // 제외할 경로 설정;
	 }
}
