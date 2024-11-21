package com.ssafy.tomorrowdiray_backend.facilities.controller;

import com.ssafy.tomorrowdiray_backend.facilities.dto.request.FacilityRequest;
import com.ssafy.tomorrowdiray_backend.facilities.dto.response.FacilityResponse;
import com.ssafy.tomorrowdiray_backend.facilities.service.FacilityService;
import com.ssafy.tomorrowdiray_backend.global.exception.StatusCode;
import com.ssafy.tomorrowdiray_backend.global.response.BaseApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/facility")
public class FacilityController {

    private final FacilityService facilityService;

    @GetMapping
    public ResponseEntity<BaseApiResponse<List<FacilityResponse>>> getFacilities(@ModelAttribute FacilityRequest request) {
        List<FacilityResponse> responses = facilityService.getFacilities(request);
        return BaseApiResponse.success(StatusCode.SHOW_FACILITY_LIST_SUCCESS, responses);
    }
}
