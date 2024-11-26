package com.ssafy.tomorrowdiray_backend.house.repository;

import com.ssafy.tomorrowdiray_backend.house.dto.response.HouseDetailResponse;
import com.ssafy.tomorrowdiray_backend.house.dto.response.HouseListResponse;
import com.ssafy.tomorrowdiray_backend.house.entity.House;

public interface HouseRepository {

    HouseListResponse selectByDongcode(String dongcode);

    HouseDetailResponse selectByAptSeq(String aptSeq);

    House selectByLocation(Double latitude, Double longitude);
}
