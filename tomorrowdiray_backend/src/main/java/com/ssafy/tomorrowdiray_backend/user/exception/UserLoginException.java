package com.ssafy.tomorrowdiray_backend.user.exception;

import com.ssafy.tomorrowdiray_backend.global.exception.ApiException;
import com.ssafy.tomorrowdiray_backend.global.exception.ErrorCode;

public class UserLoginException extends ApiException {
	public UserLoginException(ErrorCode errorCode) {
		super(errorCode);
	}
}
