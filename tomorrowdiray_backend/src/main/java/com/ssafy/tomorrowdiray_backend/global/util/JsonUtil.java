package com.ssafy.tomorrowdiray_backend.global.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class JsonUtil {

    public static String cleanUpJson(String rawJson) {
        rawJson = rawJson.trim()
                .replaceAll("^`+|`+$", "");
        if (rawJson.startsWith("json")) {
            rawJson = rawJson.substring(4).trim();
        }
        return rawJson;
    }

    public static Map<String, Object> parseJsonToMap(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, Map.class);
        } catch (Exception e) {
            throw new RuntimeException("JSON 파싱 오류", e);
        }
    }
}
