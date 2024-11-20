package com.ssafy.tomorrowdiray_backend.global.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StatusCode implements BaseCode{

	// ----- [User] ----- //
	SIGNUP_SUCCESS(HttpStatus.OK, "회원가입 성공");

	private final HttpStatus status;
	private final String message;
}
