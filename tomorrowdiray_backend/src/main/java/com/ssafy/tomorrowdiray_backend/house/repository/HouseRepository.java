package com.ssafy.tomorrowdiray_backend.house.repository;

import com.ssafy.tomorrowdiray_backend.house.dto.HouseDealInfo;
import com.ssafy.tomorrowdiray_backend.house.dto.response.HouseDetailResponse;
import com.ssafy.tomorrowdiray_backend.house.entity.House;

import java.util.List;

public interface HouseRepository {

    List<House> selectByDongcode(String dongcode);

    HouseDetailResponse selectByAptSeq(String aptSeq);

    House selectByLocation(Double latitude, Double longitude);
}
