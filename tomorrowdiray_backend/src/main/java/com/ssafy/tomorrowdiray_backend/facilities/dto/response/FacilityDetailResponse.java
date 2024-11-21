package com.ssafy.tomorrowdiray_backend.facilities.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FacilityDetailResponse {

    private Long id;
    private String name;
    private String category;
    private String roadAddress;

    @Builder
    public FacilityDetailResponse(Long id, String name, String category, String roadAddress) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.roadAddress = roadAddress;
    }
}
