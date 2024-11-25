package com.ssafy.tomorrowdiray_backend.diary.service;

import com.ssafy.tomorrowdiray_backend.diary.dto.response.CreateContentResponse;
import com.ssafy.tomorrowdiray_backend.global.util.FileUtil;
import com.ssafy.tomorrowdiray_backend.image.service.S3ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.image.Image;
import org.springframework.ai.image.ImageGeneration;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DalleService {

    private final OpenAiImageModel openAiImageModel;
    private final S3ImageService s3ImageService;

    public List<String> generateImage(List<CreateContentResponse> diaryContentList, String summary) {
        String combinedPrompt = diaryContentList.stream()
                .map(CreateContentResponse::getContent)
                .map(content -> "- " + content) // 각 프롬프트를 구분하기 위해 "-" 추가
                .collect(Collectors.joining("\n"))
                + "\n\nSummary:\n" + summary;

        ImageResponse response = openAiImageModel.call(
                new ImagePrompt(combinedPrompt,
                        OpenAiImageOptions.builder()
                                .withQuality("hd")
                                .withN(5)
                                .withHeight(1024)
                                .withWidth(1024)
                                .withModel("dall-e-2")
                                .withResponseFormat("b64_json")
                                .build())
        );

        List<MultipartFile> files = response.getResults().stream()
                .map(ImageGeneration::getOutput)
                .map(Image::getB64Json)
                .map(FileUtil::convertBase64ToMultipartFile)
                .toList();

        return makeFileToUrl(files);
    }

    private List<String> makeFileToUrl(List<MultipartFile> files) {
        return files.stream()
                .map(s3ImageService::upload)
                .toList();
    }
}
