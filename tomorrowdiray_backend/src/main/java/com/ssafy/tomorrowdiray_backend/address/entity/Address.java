package com.ssafy.tomorrowdiray_backend.address.entity;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Address {
    private String dongCode;
    private String sidoName;
    private String gugunName;
    private String dongName;
}
