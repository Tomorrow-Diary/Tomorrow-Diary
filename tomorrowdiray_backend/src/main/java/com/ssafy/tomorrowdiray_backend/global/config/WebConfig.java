package com.ssafy.tomorrowdiray_backend.global.config;

import com.ssafy.tomorrowdiray_backend.global.permission.AuthInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

	private final AuthInterceptor authInterceptor;

//	 @Override
//	 public void addInterceptors(InterceptorRegistry registry) {
//		 registry.addInterceptor(authInterceptor)
//				 .addPathPatterns("/**")
//				 .excludePathPatterns(
//						 "/api/v1/user/login/**",
//						 "/api/v1/user/signup",
//						 "/swagger-resources/**",
//						 "/swagger-ui/**",
//						 "/v3/api-docs/**",
//						 "/callback"
//				 );
//	 }

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOriginPatterns("http://localhost:5174")
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD")
				.allowedHeaders("*")
				.exposedHeaders("Authorization", "Content-Type")
				.allowCredentials(true)
				.maxAge(3600);
	}
}
