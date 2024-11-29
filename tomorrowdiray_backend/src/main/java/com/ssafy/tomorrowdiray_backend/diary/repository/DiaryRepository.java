package com.ssafy.tomorrowdiray_backend.diary.repository;

import com.ssafy.tomorrowdiray_backend.diary.entity.Diary;
import com.ssafy.tomorrowdiray_backend.diary.entity.DiaryContent;
import com.ssafy.tomorrowdiray_backend.diary.entity.DiaryFacility;
import com.ssafy.tomorrowdiray_backend.diary.entity.DiaryImage;

import java.util.List;

public interface DiaryRepository {

    void insertDiary(Diary diary);

    void insertDiaryContents(List<DiaryContent> contents);

    void insertDiaryFacilities(List<DiaryFacility> diaryFacilities);

    void insertDiaryImages(List<DiaryImage> diaryImageList);

    void insertDiaryImage(DiaryImage diaryImage);

    Diary selectByDiaryId(Long diaryId);
}
