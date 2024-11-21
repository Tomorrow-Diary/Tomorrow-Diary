package com.ssafy.tomorrowdiray_backend.facilities.service;

import com.ssafy.tomorrowdiray_backend.facilities.dto.request.FacilityRequest;
import com.ssafy.tomorrowdiray_backend.facilities.dto.response.FacilityDetailResponse;
import com.ssafy.tomorrowdiray_backend.facilities.dto.response.FacilityResponse;
import com.ssafy.tomorrowdiray_backend.facilities.repository.FacilityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FacilityService {

    private final FacilityRepository facilityRepository;

    public List<FacilityResponse> getFacilities(FacilityRequest request) {
        return facilityRepository.selectByCategoryAndCoordinate(request);
    }

    public FacilityDetailResponse getFacility(Long id) {
        return facilityRepository.selectById(id);
    }
}
