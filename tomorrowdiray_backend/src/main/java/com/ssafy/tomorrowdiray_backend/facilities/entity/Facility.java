package com.ssafy.tomorrowdiray_backend.facilities.entity;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Facility {
    private Long id;                     // 시설 ID
    private String name;                 // 시설 이름
    private String roadAddress;          // 도로명 주소
    private Double longitude;            // 경도
    private Double latitude;             // 위도
    private FacilityCategory facilityCategory; // 시설 카테고리

    @Builder
    public Facility(Long id, String name, String roadAddress, Double longitude, Double latitude, FacilityCategory facilityCategory) {
        this.id = id;
        this.name = name;
        this.roadAddress = roadAddress;
        this.longitude = longitude;
        this.latitude = latitude;
        this.facilityCategory = facilityCategory;
    }
}
