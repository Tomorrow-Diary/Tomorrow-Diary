package com.ssafy.tomorrowdiray_backend.diary.controller;

import com.ssafy.tomorrowdiray_backend.diary.dto.request.DiaryRequest;
import com.ssafy.tomorrowdiray_backend.diary.dto.response.DiaryResponse;
import com.ssafy.tomorrowdiray_backend.diary.service.DiaryService;
import com.ssafy.tomorrowdiray_backend.global.exception.StatusCode;
import com.ssafy.tomorrowdiray_backend.global.response.BaseApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/diary")
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping
    public ResponseEntity<BaseApiResponse<String>> createDiary(@RequestBody DiaryRequest request) {
        String response = diaryService.createDiary(request);
        return BaseApiResponse.success(StatusCode.CREATE_DIARY_SUCCESS, response);
    }
}
