package com.ssafy.tomorrowdiray_backend.user.dto.request;

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
    private String transportType;
    @Schema(description = "출근 시간", example = "07:30:00")
    private LocalTime startTime;

    @Schema(description = "퇴근 시간", example = "18:00:00")
    private LocalTime endTime;

    @Builder
    public SignupRequest(String roadAddress, double latitude, double longitude, String transportType, LocalTime startTime, LocalTime endTime) {
        this.roadAddress = roadAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.transportType = transportType;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
