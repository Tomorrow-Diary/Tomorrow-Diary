package com.ssafy.tomorrowdiray_backend.diary.dto.response;

import com.ssafy.tomorrowdiray_backend.diary.entity.Diary;
import com.ssafy.tomorrowdiray_backend.facilities.entity.Facility;
import com.ssafy.tomorrowdiray_backend.user.entity.UserDestination;
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
    private String summary;
    private String summaryImgUrl;
    private List<CreateContentResponse> contents;
    private List<Facility> facilities;
    private UserDestination userDestination;

    @Builder
    public DiaryResponse(Long id, String date, String weather, List<CreateContentResponse> contents, String summary, String summaryImgUrl, List<Facility> facilities, UserDestination userDestination) {
        this.id = id;
        this.date = date;
        this.weather = weather;
        this.contents = contents;
        this.summary = summary;
        this.summaryImgUrl = summaryImgUrl;
        this.facilities = facilities;
        this.userDestination = userDestination;
    }

    public static DiaryResponse toDto(Diary diary, LocalDate date, String weather, List<CreateContentResponse> contents, String summary, List<String> imgUrlList, List<Facility> facilities, UserDestination userDestination) {
        List<CreateContentResponse> updatedContents = CreateContentResponse.mapImgUrls(contents, imgUrlList);

        return DiaryResponse.builder()
                .id(diary.getId())
                .date(date.toString())
                .weather(weather)
                .contents(updatedContents)
                .summary(summary)
                .summaryImgUrl(imgUrlList.get(imgUrlList.size() - 1))
                .facilities(facilities)
                .userDestination(userDestination)
                .build();
    }
}
