package com.ssafy.tomorrowdiray_backend.diary.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@NoArgsConstructor
public class DiaryRequest {
    private List<String> facilities;
    private Double latitude;
    private Double longitude;
}
