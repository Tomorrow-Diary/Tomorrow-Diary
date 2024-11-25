package com.ssafy.tomorrowdiray_backend.facilities.service;

import com.ssafy.tomorrowdiray_backend.facilities.exception.FacilityException;
import com.ssafy.tomorrowdiray_backend.facilities.dto.request.FacilityRequest;
import com.ssafy.tomorrowdiray_backend.facilities.dto.response.FacilityDetailResponse;
import com.ssafy.tomorrowdiray_backend.facilities.dto.response.FacilityResponse;
import com.ssafy.tomorrowdiray_backend.facilities.entity.Facility;
import com.ssafy.tomorrowdiray_backend.facilities.repository.FacilityRepository;
import com.ssafy.tomorrowdiray_backend.global.exception.ErrorCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

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

	public Facility getRandomFacility(Double latitude, Double longitude, String category) {
		List<Facility> facilities = facilityRepository.findByLocationAndCategory(latitude, longitude, category);
		if (facilities == null || facilities.isEmpty())
			throw new FacilityException(ErrorCode.NOT_EXIST_FACILITY);
		return selectRandomFacility(facilities);
	}

	private Facility selectRandomFacility(List<Facility> facilities) {
		Random random = new Random();
		int randomIndex = random.nextInt(facilities.size());
		return facilities.get(randomIndex);
	}
}
