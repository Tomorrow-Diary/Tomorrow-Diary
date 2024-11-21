package com.ssafy.tomorrowdiray_backend.facilities.controller;

import com.ssafy.tomorrowdiray_backend.facilities.dto.request.FacilityRequest;
import com.ssafy.tomorrowdiray_backend.facilities.dto.response.FacilityDetailResponse;
import com.ssafy.tomorrowdiray_backend.facilities.dto.response.FacilityResponse;
import com.ssafy.tomorrowdiray_backend.facilities.service.FacilityService;
import com.ssafy.tomorrowdiray_backend.global.exception.StatusCode;
import com.ssafy.tomorrowdiray_backend.global.response.BaseApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/facility")
public class FacilityController {
    private final FacilityService facilityService;

    @Operation(summary = "편의시설 리스트 조회 API", description = "카테고리명과 map의 좌하단 좌표, 우상단 좌표로 편의시설 리스트를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "편의시설 리스트 조회 성공")
    })
    @GetMapping
    public ResponseEntity<BaseApiResponse<List<FacilityResponse>>> getFacilities(@ModelAttribute FacilityRequest request) {
        List<FacilityResponse> responses = facilityService.getFacilities(request);
        return BaseApiResponse.success(StatusCode.SHOW_FACILITY_LIST_SUCCESS, responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseApiResponse<FacilityDetailResponse>> getFacility(@PathVariable Long id) {
        FacilityDetailResponse response = facilityService.getFacility(id);
        return BaseApiResponse.success(StatusCode.SHOW_FACILITY_SUCCESS, response);
    }
}
