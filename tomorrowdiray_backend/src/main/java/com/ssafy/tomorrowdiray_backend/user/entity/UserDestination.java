package com.ssafy.tomorrowdiray_backend.user.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserDestination {
    private Long id;
    private String destinationAddress;
    private double latitude;
    private double longitude;

    @Builder
    public UserDestination(Long id, String destinationAddress, double latitude, double longitude) {
        this.id = id;
        this.destinationAddress = destinationAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
