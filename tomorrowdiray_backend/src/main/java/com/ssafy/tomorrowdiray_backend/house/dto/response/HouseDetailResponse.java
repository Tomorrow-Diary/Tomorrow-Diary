package com.ssafy.tomorrowdiray_backend.house.dto.response;

import com.ssafy.tomorrowdiray_backend.house.dto.HouseDealInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class HouseDetailResponse {
    private String aptSeq;
    private String name;
    private int buildYear;
    private String roadAddress;
    private String jibunAddress;

    private List<HouseDealInfo> houseDealInfoList;

    @Builder
    public HouseDetailResponse(String aptSeq, String name, int buildYear, String roadAddress, String jibunAddress, List<HouseDealInfo> houseDealInfoList) {
        this.aptSeq = aptSeq;
        this.name = name;
        this.buildYear = buildYear;
        this.roadAddress = roadAddress;
        this.jibunAddress = jibunAddress;
        this.houseDealInfoList = houseDealInfoList;
    }
}
