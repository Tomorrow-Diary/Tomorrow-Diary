package com.ssafy.tomorrowdiray_backend.diary.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

import com.ssafy.tomorrowdiray_backend.diary.entity.DiaryContent;


@Getter
@NoArgsConstructor
public class DiaryResponse {
    // private Long id;
    private String date;
    private String weather;
    private List<DiaryContent> contents;
    private String summary;

    @Builder
    public DiaryResponse(String date, String weather, List<DiaryContent> contents, String summary) {
        // this.id = id;
        this.date = date;
        this.weather = weather;
        this.contents = contents;
        this.summary = summary;
    }

    public static DiaryResponse toDto(String date, String weather, List<DiaryContent> contents, String summary) {
        return DiaryResponse.builder()
            .date(date)
            .weather(weather)
            .contents(contents)
            .summary(summary)
            .build();
    }
}
