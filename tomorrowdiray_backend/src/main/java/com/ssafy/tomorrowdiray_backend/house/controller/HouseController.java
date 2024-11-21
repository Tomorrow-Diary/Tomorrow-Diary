package com.ssafy.tomorrowdiray_backend.house.controller;

import com.ssafy.tomorrowdiray_backend.global.exception.StatusCode;
import com.ssafy.tomorrowdiray_backend.global.response.BaseApiResponse;
import com.ssafy.tomorrowdiray_backend.house.dto.response.HouseDetailResponse;
import com.ssafy.tomorrowdiray_backend.house.dto.response.HouseResponse;
import com.ssafy.tomorrowdiray_backend.house.service.HouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/house")
@RequiredArgsConstructor
public class HouseController {
    private final HouseService houseService;

    @Operation(summary = "아파트 리스트 조회 API", description = "동코드로 아파트 리스트를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "아파트 조회 성공")
    })
    @GetMapping
    public ResponseEntity<BaseApiResponse<List<HouseResponse>>> getHouses(@RequestParam String dongcode) {
        List<HouseResponse> responses = houseService.getHouses(dongcode);
        return BaseApiResponse.success(StatusCode.SHOW_HOUSE_LIST_SUCCESS, responses);
    }

    @GetMapping("/{aptSeq}")
    public ResponseEntity<BaseApiResponse<HouseDetailResponse>> getHouse(@PathVariable String aptSeq) {
        HouseDetailResponse response = houseService.getHouseByAptSeq(aptSeq);
        return BaseApiResponse.success(StatusCode.SHOW_HOUSE_SUCCESS, response);
    }
}
