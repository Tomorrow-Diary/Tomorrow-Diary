package com.ssafy.tomorrowdiray_backend.user.dto.request;

import com.ssafy.tomorrowdiray_backend.user.entity.RouteType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class SignupRequest {
    private String roadAddress;
    private double latitude;
    private double longitude;
    private RouteType routeType;
    private LocalTime startTime;
    private LocalTime endTime;

    @Builder
    public SignupRequest(String roadAddress, double latitude, double longitude, String routeType, LocalTime startTime, LocalTime endTime) {
        this.roadAddress = roadAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.routeType = RouteType.fromString(routeType);
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
