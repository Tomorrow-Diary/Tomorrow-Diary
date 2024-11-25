package com.ssafy.tomorrowdiray_backend.facilities.repository;

import com.ssafy.tomorrowdiray_backend.facilities.dto.request.FacilityRequest;
import com.ssafy.tomorrowdiray_backend.facilities.dto.response.FacilityDetailResponse;
import com.ssafy.tomorrowdiray_backend.facilities.dto.response.FacilityResponse;

import java.util.List;

public interface FacilityRepository {
    List<FacilityResponse> selectByCategoryAndCoordinate(FacilityRequest request);

    FacilityDetailResponse selectById(Long id);
}
