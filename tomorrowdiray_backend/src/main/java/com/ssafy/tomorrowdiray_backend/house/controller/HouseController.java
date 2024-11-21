package com.ssafy.tomorrowdiray_backend.house.controller;

import com.ssafy.tomorrowdiray_backend.global.exception.StatusCode;
import com.ssafy.tomorrowdiray_backend.global.response.BaseApiResponse;
import com.ssafy.tomorrowdiray_backend.house.dto.response.HouseResponse;
import com.ssafy.tomorrowdiray_backend.house.service.HouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/house")
@RequiredArgsConstructor
public class HouseController {
    private final HouseService houseService;

    @GetMapping
    public ResponseEntity<BaseApiResponse<List<HouseResponse>>> getHouses(@RequestParam String dongcode) {
        List<HouseResponse> responses = houseService.getHouses(dongcode);
        return BaseApiResponse.success(StatusCode.SHOW_HOUSE_SUCCESS, responses);
    }
}
