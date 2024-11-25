package com.ssafy.tomorrowdiray_backend.image.exception;

import com.ssafy.tomorrowdiray_backend.global.exception.ApiException;
import com.ssafy.tomorrowdiray_backend.global.exception.BaseCode;

public class S3Exception extends ApiException {
    public S3Exception(BaseCode code) {
        super(code);
    }
}
