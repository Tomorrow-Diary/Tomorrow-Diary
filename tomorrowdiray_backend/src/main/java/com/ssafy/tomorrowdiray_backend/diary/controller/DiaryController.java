package com.ssafy.tomorrowdiray_backend.diary.controller;

import com.ssafy.tomorrowdiray_backend.diary.dto.request.DiaryRequest;
import com.ssafy.tomorrowdiray_backend.diary.dto.response.DiaryResponse;
import com.ssafy.tomorrowdiray_backend.diary.service.DiaryService;
import com.ssafy.tomorrowdiray_backend.global.exception.StatusCode;
import com.ssafy.tomorrowdiray_backend.global.response.BaseApiResponse;
import com.ssafy.tomorrowdiray_backend.user.entity.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/diary")
public class DiaryController {

    private final DiaryService diaryService;

    @Operation(summary = "일기 생성 API", description = "일기를 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "일기 생성 성공")
    })
    @PostMapping
    public ResponseEntity<BaseApiResponse<DiaryResponse>> createDiary(
        @RequestBody DiaryRequest request,
        @Parameter(hidden = true) @SessionAttribute User user) {
        DiaryResponse response = diaryService.createDiary(request, user);
        return BaseApiResponse.success(StatusCode.CREATE_DIARY_SUCCESS, response);
    }
}
