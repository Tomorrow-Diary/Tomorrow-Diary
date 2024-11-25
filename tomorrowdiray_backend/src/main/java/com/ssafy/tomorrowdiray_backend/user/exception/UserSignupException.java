package com.ssafy.tomorrowdiray_backend.user.exception;

import com.ssafy.tomorrowdiray_backend.global.exception.ApiException;
import com.ssafy.tomorrowdiray_backend.global.exception.ErrorCode;

public class UserSignupException extends ApiException {
    public UserSignupException(ErrorCode errorCode) {
        super(errorCode);
    }
}
