package com.ssafy.tomorrowdiray_backend.global.exception;

import org.springframework.http.HttpStatus;

public interface BaseCode {
	HttpStatus getStatus();
	String getMessage();
}
