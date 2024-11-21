package com.ssafy.tomorrowdiray_backend.address.controller;

import com.ssafy.tomorrowdiray_backend.address.dto.response.SearchAddressResponse;
import com.ssafy.tomorrowdiray_backend.address.service.AddressService;
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
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    @Operation(summary = "주소 검색 API", description = "키워드로 주소를 검색합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "주소 조회 성공")
    })
    @GetMapping
    public ResponseEntity<BaseApiResponse<List<SearchAddressResponse>>> searchAddress(@RequestParam String keyword) {
        List<SearchAddressResponse> responses = addressService.search(keyword);
        return BaseApiResponse.success(StatusCode.SEARCH_ADDRESS_SUCCESS, responses);
    }
}
