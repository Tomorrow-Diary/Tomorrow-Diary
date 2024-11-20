package com.ssafy.tomorrowdiray_backend.global.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode implements BaseCode{

	// ----- [User] ----- //
	UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "접근할 수 없는 유저입니다."),
	NOT_EXIST_ROUTE_TYPE(HttpStatus.BAD_REQUEST,"존재하지 않는 이동 수단입니다." );

	private final HttpStatus status;
	private final String message;
}
