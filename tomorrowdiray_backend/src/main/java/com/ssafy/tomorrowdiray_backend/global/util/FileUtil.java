package com.ssafy.tomorrowdiray_backend.global.util;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

public class FileUtil {
    public static MultipartFile convertBase64ToMultipartFile(String base64Data) {
        byte[] decodedBytes = Base64.getDecoder().decode(base64Data);
        String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"))+".png";

        return new MockMultipartFile(
                fileName, // 파일 이름
                fileName, // 원래 파일 이름
                "image/png", // MIME 타입
                decodedBytes // 디코딩된 이미지 데이터
        );
    }
}
