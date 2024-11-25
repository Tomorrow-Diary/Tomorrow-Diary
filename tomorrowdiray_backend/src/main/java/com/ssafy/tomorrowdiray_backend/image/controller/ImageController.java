package com.ssafy.tomorrowdiray_backend.image.controller;

import com.ssafy.tomorrowdiray_backend.global.exception.StatusCode;
import com.ssafy.tomorrowdiray_backend.global.response.BaseApiResponse;
import com.ssafy.tomorrowdiray_backend.image.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/image")
public class ImageController {

    private final ImageService imageService;

    @Operation(summary = "이미지 업로드 API", description = "이미지 파일을 S3에 업로드합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "이미지 업로드 성공")
    })
    @PostMapping("/{diaryId}")
    public ResponseEntity<BaseApiResponse<Void>> createImage(
            @PathVariable Long diaryId,
            @RequestParam("image") MultipartFile image) {
        imageService.saveImage(image, diaryId);
        return BaseApiResponse.success(StatusCode.CREATE_IMAGE_SUCCESS);
    }
}
