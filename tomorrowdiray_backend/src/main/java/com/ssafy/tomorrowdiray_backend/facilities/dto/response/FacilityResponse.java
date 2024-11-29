package com.ssafy.tomorrowdiray_backend.facilities.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FacilityResponse {
    private Long id;
    private String name;
    private Double latitude;
    private Double longitude;

    @Builder
    public FacilityResponse(Long id, String name, Double latitude, Double longitude) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
