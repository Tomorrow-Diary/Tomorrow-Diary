package com.ssafy.tomorrowdiray_backend.diary.exception;

import com.ssafy.tomorrowdiray_backend.global.exception.ApiException;
import com.ssafy.tomorrowdiray_backend.global.exception.ErrorCode;

public class DiaryException extends ApiException {
	public DiaryException(ErrorCode code) {
		super(code);
	}
}
