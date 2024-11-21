package com.ssafy.tomorrowdiray_backend.facilities.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FacilityRequest {
    private String category;
    private double minLatitude;
    private double minLongitude;
    private double maxLatitude;
    private double maxLongitude;

    @Builder
    public FacilityRequest(String category, double minLatitude, double minLongitude, double maxLatitude, double maxLongitude) {
        this.category = category;
        this.minLatitude = minLatitude;
        this.minLongitude = minLongitude;
        this.maxLatitude = maxLatitude;
        this.maxLongitude = maxLongitude;
    }
}
