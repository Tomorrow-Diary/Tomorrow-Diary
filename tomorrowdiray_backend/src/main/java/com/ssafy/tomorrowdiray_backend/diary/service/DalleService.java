package com.ssafy.tomorrowdiray_backend.diary.service;

import com.ssafy.tomorrowdiray_backend.diary.dto.response.CreateContentResponse;
import com.ssafy.tomorrowdiray_backend.global.util.FileUtil;
import com.ssafy.tomorrowdiray_backend.image.service.S3ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.image.Image;
import org.springframework.ai.image.ImageGeneration;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DalleService {

    private final OpenAiImageModel openAiImageModel;
    private final S3ImageService s3ImageService;

    public List<String> generateImage(List<CreateContentResponse> diaryContentList) {
        return diaryContentList.stream()
                .flatMap(contentResponse -> {
                    String prompt = "따뜻하고 현실적인 스타일로 \n" +
                            "- " + contentResponse.getContent() +
                            "\n 위의 내용을 기반으로 사람이 포함된 2D 그림을 만들어줘.";

                    log.debug("Generated prompt: {}", prompt);

                    // 각 프롬프트에 대해 4개의 이미지를 생성
                    ImageResponse response = openAiImageModel.call(
                            new ImagePrompt(prompt,
                                    OpenAiImageOptions.builder()
                                            .withUser("너는 한국인이야. 종교가 없어.")
                                            .withN(1)
                                            .withHeight(1024)
                                            .withWidth(1024)
                                            .withModel("dall-e-3")
                                            .withResponseFormat("b64_json")
                                            .build())
                    );

                    List<MultipartFile> files = response.getResults().stream()
                            .map(ImageGeneration::getOutput)
                            .map(Image::getB64Json)
                            .map(FileUtil::convertBase64ToMultipartFile)
                            .toList();

                    return makeFileToUrl(files).stream();
                })
                .toList();
    }

    private List<String> makeFileToUrl(List<MultipartFile> files) {
        return files.stream()
                .map(s3ImageService::upload)
                .toList();
    }
}
