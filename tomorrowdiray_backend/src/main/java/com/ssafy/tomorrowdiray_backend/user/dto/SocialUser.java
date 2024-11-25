package com.ssafy.tomorrowdiray_backend.user.dto;

import com.ssafy.tomorrowdiray_backend.user.entity.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SocialUser {
	private Long socialId;
	private String nickname;
	private User user;

	@Builder
	public SocialUser(Long socialId, String nickname, User user) {
		this.socialId = socialId;
		this.nickname = nickname;
		this.user = user;
	}

	public boolean isExistUser() {
		return this.user != null;
	}
}
