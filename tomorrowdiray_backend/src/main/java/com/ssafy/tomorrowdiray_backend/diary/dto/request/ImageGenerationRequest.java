package com.ssafy.tomorrowdiray_backend.diary.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ImageGenerationRequest {
    private String prompt;
    private int n;
    private String size;
    private String responseFormat;
    private String model;

    @Builder
    public ImageGenerationRequest(String prompt, int n, String size, String responseFormat, String model) {
        this.prompt = prompt;
        this.n = n;
        this.size = size;
        this.responseFormat = responseFormat;
        this.model = model;
    }
}