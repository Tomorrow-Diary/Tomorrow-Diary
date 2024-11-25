package com.ssafy.tomorrowdiray_backend.image.controller;

import com.ssafy.tomorrowdiray_backend.global.exception.StatusCode;
import com.ssafy.tomorrowdiray_backend.global.response.BaseApiResponse;
import com.ssafy.tomorrowdiray_backend.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/image")
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/{diaryId}")
    public ResponseEntity<BaseApiResponse<Void>> createImage(
            @PathVariable Long diaryId,
            @RequestParam("image") MultipartFile image) {
        imageService.saveImage(image, diaryId);
        return BaseApiResponse.success(StatusCode.CREATE_IMAGE_SUCCESS);
    }
}
