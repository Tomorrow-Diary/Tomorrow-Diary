package com.ssafy.tomorrowdiray_backend.house.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class HouseDealInfo {
    private Long id;
    private int floor;
    private LocalDate dealDate;
    private Double userArea;
    private String dealAmount;

    @Builder
    public HouseDealInfo(Long id, int floor, LocalDate dealDate, Double userArea, String dealAmount) {
        this.id = id;
        this.floor = floor;
        this.dealDate = dealDate;
        this.userArea = userArea;
        this.dealAmount = dealAmount;
    }
}
