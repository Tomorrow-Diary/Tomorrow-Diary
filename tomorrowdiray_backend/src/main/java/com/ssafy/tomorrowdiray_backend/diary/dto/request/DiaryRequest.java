package com.ssafy.tomorrowdiray_backend.diary.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class DiaryRequest {
    private List<String> facilities;
    private Double latitude;
    private Double longitude;
}
