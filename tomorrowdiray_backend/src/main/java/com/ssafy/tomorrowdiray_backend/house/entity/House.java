package com.ssafy.tomorrowdiray_backend.house.entity;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class House {
    private String aptSeq;          // 아파트 고유 식별자
    private String sggCd;           // 시군구 코드
    private String umdCd;           // 읍면동 코드
    private String umdNm;           // 읍면동 이름
    private String jibun;           // 지번
    private String roadNmSggCd;     // 도로명 시군구 코드
    private String roadNm;          // 도로명
    private String roadNmBonbun;    // 도로명 본번
    private String roadNmBubun;     // 도로명 부번
    private String aptNm;           // 아파트 이름
    private int buildYear;          // 건축 연도
    private Double latitude;        // 위도
    private Double longitude;       // 경도
}
