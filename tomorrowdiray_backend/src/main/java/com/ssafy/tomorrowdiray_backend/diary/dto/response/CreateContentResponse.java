package com.ssafy.tomorrowdiray_backend.diary.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.IntStream;

@Getter
@NoArgsConstructor
public class CreateContentResponse {
    private LocalTime time;
    private String content;
    private String imgUrl;

    @Builder
    public CreateContentResponse(LocalTime time, String content, String imgUrl) {
        this.time = time;
        this.content = content;
        this.imgUrl = imgUrl;
    }

    public CreateContentResponse withImgUrl(String imgUrl) {
        return CreateContentResponse.builder()
                .time(this.time)
                .content(this.content)
                .imgUrl(imgUrl)
                .build();
    }

    public static List<CreateContentResponse> mapImgUrls(List<CreateContentResponse> contents, List<String> imgUrlList) {
        return IntStream.range(0, contents.size())
                .mapToObj(i -> contents.get(i).withImgUrl(imgUrlList.get(i)))
                .toList();
    }
}
