package com.ssafy.tomorrowdiray_backend.user.dto.request;

import com.ssafy.tomorrowdiray_backend.user.entity.RouteType;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "출근 시간", example = "07:30:00")
    private LocalTime startTime;

    @Schema(description = "퇴근 시간", example = "18:00:00")
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
