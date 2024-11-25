package com.ssafy.tomorrowdiray_backend.diary.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiaryImage {
    private Long id;
    private boolean isRouteImg;
    private String url;
    private LocalDateTime createdAt;
    private Diary diary;

    @Builder
    public DiaryImage(Long id, boolean isRouteImg, String url, Diary diary) {
        this.id = id;
        this.isRouteImg = isRouteImg;
        this.url = url;
        this.diary = diary;
    }

    public static DiaryImage toEntity(boolean isRouteImg, String url, Diary diary) {
        return DiaryImage.builder()
                .isRouteImg(isRouteImg)
                .url(url)
                .diary(diary)
                .build();
    }
}
