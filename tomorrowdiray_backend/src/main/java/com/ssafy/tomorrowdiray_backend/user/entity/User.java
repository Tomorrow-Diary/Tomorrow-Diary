package com.ssafy.tomorrowdiray_backend.user.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long socialId;
	private String nickname;
	private LocalTime startTime;
	private LocalTime endTime;
	private Role role;
	private RouteType transportType;
	private UserDestination userDestination;

	@Builder
	public User(Long id, Long socialId, String nickname, LocalTime startTime, LocalTime endTime, Role role, RouteType transportType, UserDestination userDestination) {
		this.id = id;
		this.socialId = socialId;
		this.nickname = nickname;
		this.startTime = startTime;
		this.endTime = endTime;
		this.role = role;
		this.transportType = transportType;
		this.userDestination = userDestination;
	}
}
