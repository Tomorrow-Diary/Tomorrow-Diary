package com.ssafy.tomorrowdiray_backend.diary.dto.response;

import com.ssafy.tomorrowdiray_backend.diary.entity.DiaryContent;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class DiaryResponse {
    private Long id;
    private LocalDate date;
    private String weather;
    private List<DiaryContent> contents;

    @Builder
    public DiaryResponse(Long id, LocalDate date, String weather, List<DiaryContent> contents) {
        this.id = id;
        this.date = date;
        this.weather = weather;
        this.contents = contents;
    }
}
