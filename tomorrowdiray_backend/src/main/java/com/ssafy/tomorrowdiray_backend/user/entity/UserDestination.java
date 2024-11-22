package com.ssafy.tomorrowdiray_backend.user.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
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

    public static UserDestination toEntity(String destinationAddress, double latitude, double longitude) {
        return UserDestination.builder()
            .destinationAddress(destinationAddress)
            .latitude(latitude)
            .longitude(longitude)
            .build();
    }
}
