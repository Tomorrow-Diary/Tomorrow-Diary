package com.ssafy.tomorrowdiray_backend.user.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {
	private String authorizationCode;

	@Builder
	public LoginRequest(String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}
}
