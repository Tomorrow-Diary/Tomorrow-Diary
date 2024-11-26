package com.ssafy.tomorrowdiray_backend.house.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class HouseListResponse {
    private Double centerLatitude;
    private Double centerLongitude;
    private List<HouseResponse> houseResponseList;
}
