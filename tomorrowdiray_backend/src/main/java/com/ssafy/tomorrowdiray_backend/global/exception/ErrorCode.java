package com.ssafy.tomorrowdiray_backend.global.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode implements BaseCode{

	// ----- [User] ----- //
	UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "접근할 수 없는 유저입니다."),
	NOT_EXIST_ROUTE_TYPE(HttpStatus.BAD_REQUEST,"존재하지 않는 이동 수단입니다." ),
	FAIL_GET_ACCESSTOKEN(HttpStatus.INTERNAL_SERVER_ERROR, "kakao 인증에 실패했습니다." ),

	// ----- [Facility] ----- //
	NOT_EXIST_FACILITY(HttpStatus.BAD_REQUEST, "주위 편의시설이 존재하지 않습니다."),

	// ----- [Diary] ----- //
	FAIL_CREATE_DIARY(HttpStatus.INTERNAL_SERVER_ERROR, "일기 생성에 실패했습니다."),

	// ----- [Image] ----- //
	EMPTY_FILE_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "이미지가 존재하지 않습니다." ),
	IO_EXCEPTION_ON_IMAGE_UPLOAD(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 업로드 중 I/O Exception이 발생했습니다." ),
	PUT_OBJECT_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "S3 버킷에 이미지 업로드를 실패했습니다." );

	private final HttpStatus status;
	private final String message;
}
