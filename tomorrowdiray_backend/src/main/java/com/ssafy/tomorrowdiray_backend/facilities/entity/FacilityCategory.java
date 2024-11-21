package com.ssafy.tomorrowdiray_backend.facilities.entity;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FacilityCategory {
    private Long id;
    private String name;

    @Builder
    public FacilityCategory(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
