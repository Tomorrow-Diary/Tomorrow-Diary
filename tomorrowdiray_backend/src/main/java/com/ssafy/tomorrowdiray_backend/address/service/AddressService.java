package com.ssafy.tomorrowdiray_backend.address.service;

import com.ssafy.tomorrowdiray_backend.address.dto.response.SearchAddressResponse;
import com.ssafy.tomorrowdiray_backend.address.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    public List<SearchAddressResponse> search(String keyword) {
        return addressRepository.selectBykeyword(keyword).stream()
                .map(SearchAddressResponse::toDto)
                .toList();
    }
}
