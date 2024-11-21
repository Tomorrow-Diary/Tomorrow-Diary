package com.ssafy.tomorrowdiray_backend.global.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StatusCode implements BaseCode{

	// ----- [User] ----- //
	SIGNUP_SUCCESS(HttpStatus.OK, "회원가입 성공"),
	LOGIN_SUCCESS(HttpStatus.OK, "로그인 성공"),
	REQUIRED_SIGNUP(HttpStatus.ACCEPTED, "회원가입이 필요합니다."),
	LOGOUT_SUCCESS(HttpStatus.OK, "로그아웃 성공"),

	// ----- [Address] ----- //
	SEARCH_ADDRESS_SUCCESS(HttpStatus.OK, "주소 검색 성공" );

	private final HttpStatus status;
	private final String message;
}
