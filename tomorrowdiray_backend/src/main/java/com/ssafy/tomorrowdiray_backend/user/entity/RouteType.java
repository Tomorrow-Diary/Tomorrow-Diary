package com.ssafy.tomorrowdiray_backend.user.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.ssafy.tomorrowdiray_backend.global.exception.ErrorCode;
import com.ssafy.tomorrowdiray_backend.user.exception.UserSignupException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RouteType {
    자가용(1), 대중교통(2), 도보(3);

    private final long order;

    // Enum 값을 문자열로 변환
    @JsonValue
    public String toValue() {
        return name();
    }

    // 문자열을 Enum으로 변환
    @JsonCreator
    public static RouteType fromString(String value) {
        for (RouteType routeType : RouteType.values()) {
            if (routeType.name().equals(value)) {
                return routeType;
            }
        }
        throw new UserSignupException(ErrorCode.NOT_EXIST_ROUTE_TYPE);
    }
}
