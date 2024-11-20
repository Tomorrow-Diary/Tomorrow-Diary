package com.ssafy.tomorrowdiray_backend.global.permission;


import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ssafy.tomorrowdiray_backend.global.exception.ApiException;
import com.ssafy.tomorrowdiray_backend.global.exception.ErrorCode;
import com.ssafy.tomorrowdiray_backend.user.entity.Role;
import com.ssafy.tomorrowdiray_backend.user.entity.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
		Exception {

		// Preflight인 경우 통과
		if (CorsUtils.isPreFlightRequest(request)) {
			return true;
		}

		// HandlerMethod가 아니라면 통과
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}

		// 세션에서 사용자 정보 가져오기
		HttpSession session = request.getSession(false);
		if (session == null) {
			log.warn("Unauthorized access: 세션이 존재하지 않음");
			throw new ApiException(ErrorCode.UNAUTHORIZED_ACCESS);
		}

		User user = (User) session.getAttribute("user");
		if (user == null) {
			log.warn("Unauthorized access: 세션에 유저 객체가 없음");
			throw new ApiException(ErrorCode.UNAUTHORIZED_ACCESS);
		}

		// 권한 검사
		if (Role.USER.equals(user.getRole())) {
			request.setAttribute("user", user);
			return true;
		}

		// 권한 불일치
		log.warn("Unauthorized access: 권한 불일치. Required: USER, Found: {}", user.getRole());
		throw new ApiException(ErrorCode.UNAUTHORIZED_ACCESS);
	}
}
