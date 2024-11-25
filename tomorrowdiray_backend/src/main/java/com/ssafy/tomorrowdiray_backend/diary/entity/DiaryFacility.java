package com.ssafy.tomorrowdiray_backend.diary.entity;

import com.ssafy.tomorrowdiray_backend.facilities.entity.Facility;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiaryFacility {
    private Long id;
    private Diary diary;
    private Facility facility;

    @Builder
    public DiaryFacility(Long id, Diary diary, Facility facility) {
        this.id = id;
        this.diary = diary;
        this.facility = facility;
    }

    public static DiaryFacility toEntity(Diary diary, Facility facility) {
        return DiaryFacility.builder()
                .diary(diary)
                .facility(facility)
                .build();
    }
}
