package com.ssafy.tomorrowdiray_backend.diary.dto.response;

import com.ssafy.tomorrowdiray_backend.diary.entity.Diary;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class DiaryResponse {
    private Long id;
    private String date;
    private String weather;
    private List<CreateContentResponse> contents;
    private String summary;
    private String summaryImgUrl;

    @Builder
    public DiaryResponse(Long id, String date, String weather, List<CreateContentResponse> contents, String summary, String summaryImgUrl) {
        this.id = id;
        this.date = date;
        this.weather = weather;
        this.contents = contents;
        this.summary = summary;
        this.summaryImgUrl = summaryImgUrl;
    }

    public static DiaryResponse toDto(Diary diary, LocalDate date, String weather, List<CreateContentResponse> contents, String summary, List<String> imgUrlList) {
        List<CreateContentResponse> updatedContents = CreateContentResponse.mapImgUrls(contents, imgUrlList);

        return DiaryResponse.builder()
                .id(diary.getId())
                .date(date.toString())
                .weather(weather)
                .contents(updatedContents)
                .summary(summary)
                .summaryImgUrl(imgUrlList.get(imgUrlList.size() - 1))
                .build();
    }
}
