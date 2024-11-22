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

    @Builder
    public DiaryContent(int id, LocalTime time, String content, boolean isFinal) {
        this.id = id;
        this.time = time;
        this.content = content;
        this.isFinal = isFinal;
    }
}
