package com.ssafy.tomorrowdiray_backend.house.service;

import com.ssafy.tomorrowdiray_backend.house.dto.response.HouseDetailResponse;
import com.ssafy.tomorrowdiray_backend.house.dto.response.HouseResponse;
import com.ssafy.tomorrowdiray_backend.house.entity.House;
import com.ssafy.tomorrowdiray_backend.house.repository.HouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HouseService {

    private final HouseRepository houseRepository;

    public List<HouseResponse> getHouses(String dongcode) {
        return houseRepository.selectByDongcode(dongcode).stream()
                .map(HouseResponse::toDto)
                .toList();
    }

    public HouseDetailResponse getHouseByAptSeq(String aptSeq) {
         return houseRepository.selectByAptSeq(aptSeq);
    }
}
