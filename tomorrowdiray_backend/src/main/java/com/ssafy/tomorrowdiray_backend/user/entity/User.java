package com.ssafy.tomorrowdiray_backend.user.entity;

import lombok.*;

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
	private TransportType transportType;
	private UserDestination userDestination;

	@Builder
	public User(Long id, Long socialId, String nickname, LocalTime startTime, LocalTime endTime, String role, TransportType transportType, UserDestination userDestination) {
		this.id = id;
		this.socialId = socialId;
		this.nickname = nickname;
		this.startTime = startTime;
		this.endTime = endTime;
		this.role = Role.fromString(role);
		this.transportType = transportType;
		this.userDestination = userDestination;
	}

	public static User toEntity(Long socialId, String nickname, LocalTime startTime, LocalTime endTime, TransportType transportType, UserDestination userDestination) {
		return User.builder()
				.socialId(socialId)
				.nickname(nickname)
				.startTime(startTime)
				.endTime(endTime)
				.transportType(transportType)
				.userDestination(userDestination)
				.build();
	}
}
