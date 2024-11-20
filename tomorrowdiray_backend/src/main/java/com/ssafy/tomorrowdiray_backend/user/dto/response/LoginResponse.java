package com.ssafy.tomorrowdiray_backend.user.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponse {
	private String nickname;

	@Builder
	public LoginResponse(String nickname) {
		this.nickname = nickname;
	}

	public static LoginResponse toDto(String nickname) {
		return LoginResponse.builder()
			.nickname(nickname)
			.build();
	}
}
