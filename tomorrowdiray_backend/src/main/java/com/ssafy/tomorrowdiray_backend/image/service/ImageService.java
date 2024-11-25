package com.ssafy.tomorrowdiray_backend.image.service;

import com.ssafy.tomorrowdiray_backend.diary.entity.Diary;
import com.ssafy.tomorrowdiray_backend.diary.entity.DiaryImage;
import com.ssafy.tomorrowdiray_backend.diary.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ImageService {

    private final DiaryService diaryService;
    private final S3ImageService s3ImageService;

    @Transactional
    public void saveImage(MultipartFile image, Long diaryId) {
        String imgUrl = s3ImageService.upload(image);
        Diary diary = diaryService.selectByDiaryId(diaryId);
        DiaryImage diaryImage = DiaryImage.toEntity(true, imgUrl, diary);

        diaryService.insertRouteImg(diaryImage);
    }
}
