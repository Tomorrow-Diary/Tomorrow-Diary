package com.ssafy.tomorrowdiray_backend.user.entity;

public enum Role {
	USER, ADMIN;

	// String 값을 Role Enum으로 변환하는 메서드
	public static Role fromString(String role) {
		if (role == null || role.isEmpty()) {
			return USER;
		}
		try {
			return Role.valueOf(role.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid role: " + role);
		}
	}
}
