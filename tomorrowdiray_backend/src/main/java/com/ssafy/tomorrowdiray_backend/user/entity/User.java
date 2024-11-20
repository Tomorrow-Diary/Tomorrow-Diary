package com.ssafy.tomorrowdiray_backend.user.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User {
	private Long id;
	private Long socialId;
	private String nickname;
	private LocalTime startTime;
	private LocalTime endTime;
	private Role role;
	private Long transportTypeId;
	private Long usersDestinationId;
}
