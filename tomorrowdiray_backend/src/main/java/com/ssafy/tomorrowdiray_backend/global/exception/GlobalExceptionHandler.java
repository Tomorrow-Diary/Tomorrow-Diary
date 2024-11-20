package com.ssafy.tomorrowdiray_backend.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ssafy.tomorrowdiray_backend.global.response.BaseApiResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	// ApiException 으로 발생한 예외 처리
	@ExceptionHandler(ApiException.class)
	protected ResponseEntity<BaseApiResponse<Void>> handleCustomException(ApiException ex) {
		BaseCode errorCode = ex.getBaseCode();
		log.warn("ApiException handling: {}", ex.getMessage());
		return ResponseEntity
			.status(errorCode.getStatus())
			.body(BaseApiResponse.fail(errorCode));
	}
}
