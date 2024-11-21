package com.ssafy.tomorrowdiray_backend.address.dto.response;

import com.ssafy.tomorrowdiray_backend.address.entity.Address;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SearchAddressResponse {
    private String dongCode;
    private String sidoName;
    private String gugunName;
    private String dongName;

    @Builder
    public SearchAddressResponse(String dongCode, String sidoName, String gugunName, String dongName) {
        this.dongCode = dongCode;
        this.sidoName = sidoName;
        this.gugunName = gugunName;
        this.dongName = dongName;
    }

    public static SearchAddressResponse toDto(Address address) {
        return SearchAddressResponse.builder()
                .dongCode(address.getDongCode())
                .sidoName(address.getSidoName())
                .gugunName(address.getGugunName())
                .dongName(address.getDongName())
                .build();
    }
}
