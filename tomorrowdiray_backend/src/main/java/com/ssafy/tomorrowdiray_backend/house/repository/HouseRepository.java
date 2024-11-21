package com.ssafy.tomorrowdiray_backend.house.repository;

import com.ssafy.tomorrowdiray_backend.house.entity.House;

import java.util.List;

public interface HouseRepository {

    List<House> selectByDongcode(String dongcode);
}
