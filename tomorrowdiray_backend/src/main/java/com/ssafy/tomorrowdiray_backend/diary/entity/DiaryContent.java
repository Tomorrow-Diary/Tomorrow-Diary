package com.ssafy.tomorrowdiray_backend.diary.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiaryContent {
    private int id;
    private LocalTime time;
    private String content;
    private boolean isFinal;
    private Diary diary;

    @Builder
    public DiaryContent(int id, LocalTime time, String content, boolean isFinal, Diary diary) {
        this.id = id;
        this.time = time;
        this.content = content;
        this.isFinal = isFinal;
        this.diary = diary;
    }

    public static DiaryContent toEntity(String summary, boolean isFinal, Diary diary) {
        return DiaryContent.builder()
                .content(summary)
                .isFinal(isFinal)
                .diary(diary)
                .build();
    }

    public static DiaryContent toEntity(String summary, boolean isFinal, Diary diary, LocalTime time) {
        return DiaryContent.builder()
                .time(time)
                .content(summary)
                .isFinal(isFinal)
                .diary(diary)
                .build();
    }
}
