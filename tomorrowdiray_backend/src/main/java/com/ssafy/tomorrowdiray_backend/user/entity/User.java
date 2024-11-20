package com.ssafy.tomorrowdiray_backend.user.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User {
	private Long id;
	private Long socialId;
	private String nickname;
	private String destinationAddress;
	private Role role;
	private Long transportTypeId;
}
