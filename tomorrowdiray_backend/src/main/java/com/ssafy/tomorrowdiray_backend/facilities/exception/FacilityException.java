package com.ssafy.tomorrowdiray_backend.facilities.exception;

import com.ssafy.tomorrowdiray_backend.global.exception.ApiException;
import com.ssafy.tomorrowdiray_backend.global.exception.ErrorCode;

public class FacilityException extends ApiException {
	public FacilityException(ErrorCode errorCode) {
		super(errorCode);
	}
}
