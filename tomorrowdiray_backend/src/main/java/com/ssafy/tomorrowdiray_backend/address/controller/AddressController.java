package com.ssafy.tomorrowdiray_backend.address.controller;

import com.ssafy.tomorrowdiray_backend.address.dto.response.SearchAddressResponse;
import com.ssafy.tomorrowdiray_backend.address.service.AddressService;
import com.ssafy.tomorrowdiray_backend.global.exception.StatusCode;
import com.ssafy.tomorrowdiray_backend.global.response.BaseApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    @GetMapping
    public ResponseEntity<BaseApiResponse<List<SearchAddressResponse>>> searchAddress(@RequestParam String keyword) {
        List<SearchAddressResponse> responses = addressService.search(keyword);
        return BaseApiResponse.success(StatusCode.SEARCH_ADDRESS_SUCCESS, responses);
    }
}
