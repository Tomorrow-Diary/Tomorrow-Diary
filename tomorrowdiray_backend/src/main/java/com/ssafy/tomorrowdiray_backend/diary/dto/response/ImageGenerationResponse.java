package com.ssafy.tomorrowdiray_backend.diary.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@NoArgsConstructor
public class ImageGenerationResponse {
    private List<ImageData> data;

    public List<String> getImgUrls() {
        return data.stream()
                .map(ImageData::getUrl)
                .toList();
    }
}
