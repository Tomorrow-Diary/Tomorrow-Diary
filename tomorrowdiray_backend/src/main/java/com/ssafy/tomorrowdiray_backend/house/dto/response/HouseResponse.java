package com.ssafy.tomorrowdiray_backend.house.dto.response;

import com.ssafy.tomorrowdiray_backend.house.entity.House;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HouseResponse {

    private String aptSeq;
    private String name;
    private double latitude;
    private double longitude;

    @Builder
    public HouseResponse(String aptSeq, String name, double latitude, double longitude) {
        this.aptSeq = aptSeq;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static HouseResponse toDto(House house) {
        return HouseResponse.builder()
                .aptSeq(house.getAptSeq())
                .name(house.getAptNm())
                .latitude(house.getLatitude())
                .longitude(house.getLongitude())
                .build();
    }
}
