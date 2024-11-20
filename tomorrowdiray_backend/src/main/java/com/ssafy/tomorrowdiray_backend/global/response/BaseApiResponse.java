package com.ssafy.tomorrowdiray_backend.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ssafy.tomorrowdiray_backend.global.exception.BaseCode;

import lombok.Builder;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseApiResponse<T> {

	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	private final String status;
	private final String message;
	private final T data;


	@Builder
	public BaseApiResponse(String status, String message, T data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public static <T> BaseApiResponse<T> success(BaseCode code) {
		return BaseApiResponse.<T>builder()
			.status(SUCCESS)
			.message(code.getMessage())
			.build();
	}

	public static <T> BaseApiResponse<T> success(BaseCode code, T data) {
		return BaseApiResponse.<T>builder()
			.status(SUCCESS)
			.message(code.getMessage())
			.data(data)
			.build();
	}

	public static <T> BaseApiResponse<T> fail(BaseCode code) {
		return BaseApiResponse.<T>builder()
			.status(FAIL)
			.message(code.getMessage())
			.build();
	}
}
