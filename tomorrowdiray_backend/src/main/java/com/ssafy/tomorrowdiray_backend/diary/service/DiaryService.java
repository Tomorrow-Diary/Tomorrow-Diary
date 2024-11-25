package com.ssafy.tomorrowdiray_backend.diary.service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.tomorrowdiray_backend.diary.dto.request.DiaryRequest;
import com.ssafy.tomorrowdiray_backend.diary.dto.response.DiaryResponse;
import com.ssafy.tomorrowdiray_backend.diary.entity.DiaryContent;
import com.ssafy.tomorrowdiray_backend.diary.entity.Weather;
import com.ssafy.tomorrowdiray_backend.diary.exception.DiaryException;
import com.ssafy.tomorrowdiray_backend.facilities.entity.Facility;
import com.ssafy.tomorrowdiray_backend.facilities.service.FacilityService;
import com.ssafy.tomorrowdiray_backend.global.exception.ErrorCode;
import com.ssafy.tomorrowdiray_backend.user.entity.User;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class DiaryService {

    private final ChatClient chatClient;
    private final FacilityService facilityService;
    private final ObjectMapper objectMapper;

    @Value("classpath:/prompt/diary-create.st")
    private Resource diaryCreate;

    public DiaryService(@Qualifier("openAiChatModel") ChatModel chatModel, FacilityService facilityService,
		ObjectMapper objectMapper) {
        chatClient = ChatClient.create(chatModel);
		this.facilityService = facilityService;
		this.objectMapper = objectMapper;
	}

    public DiaryResponse createDiary(DiaryRequest request, User user) {
        // 랜덤 시설 정보 가져오기
        Facility firstFacility = facilityService.getRandomFacility(request.getLatitude(), request.getLongitude(),
            request.getFacilities().get(0));
        Facility secondFacility = facilityService.getRandomFacility(request.getLatitude(), request.getLongitude(),
            request.getFacilities().get(1));
        // 날씨 정보 생성
        String weather = Weather.getRandomWeather();
        // 날짜 정보 생성
        LocalDate tomorrow = LocalDate.now().plusDays(1);

        Map<String, Object> params = makeAIParams(request, user, firstFacility, secondFacility, weather, tomorrow);

        log.debug("params: {}", params);

        String content = chatClient.prompt()
                .user(userSpec -> userSpec
                        .text(diaryCreate)
                        .params(params))
                .call()
                .content();
        log.debug("Raw AI response content: {}", content);

        // JSON 클린업
        String cleanedContent = cleanUpJson(content);
        log.debug("Cleaned JSON content: {}", cleanedContent);

        try {
            Map<String, Object> responseMap = objectMapper.readValue(cleanedContent, Map.class);

            List<Map<String, String>> rawContents = (List<Map<String, String>>) responseMap.get("contents");
            List<DiaryContent> contents = rawContents.stream()
                .map(rawContent -> DiaryContent.builder()
                    .time(rawContent.get("time"))
                    .content(rawContent.get("content"))
                    .build())
                .toList();

            String summary = (String) responseMap.get("summary");

            return DiaryResponse.toDto(tomorrow.toString(), weather, contents, summary);
        } catch (Exception e) {
            log.error("ChatGPT 응답 매핑 오류", e);
            throw new DiaryException(ErrorCode.FAIL_CREATE_DIARY);
        }
    }

    private String cleanUpJson(String rawJson) {
        // 불필요한 문자 제거 (예: 백틱 등)
        rawJson = rawJson.trim()
            .replaceAll("^`+|`+$", "");
        if (rawJson.startsWith("json")) {
            rawJson = rawJson.substring(4).trim();
        }
        return rawJson;
    }

    private Map<String, Object> makeAIParams(DiaryRequest request, User user, Facility firstFacility,
        Facility secondFacility, String weather, LocalDate tomorrow) {
        Map<String, Object> params = new HashMap<>();
        // 현재 위치
        params.put("start_latitude", request.getLatitude());
        params.put("start_longitude", request.getLongitude());
        // 학교/회사 이름
        params.put("destination", user.getUserDestination().getDestinationAddress());
        // 기상 시간
        params.put("wakeup_time", user.getStartTime().minusHours(2));
        // 출근/퇴근 시간
        params.put("start_time", user.getStartTime().minusHours(1));
        params.put("end_time", user.getEndTime());
        params.put("enjoy_time", user.getEndTime().plusHours(2));
        // 편의시설 이름
        params.put("first_facility", firstFacility.getName());
        params.put("second_facility", secondFacility.getName());
        // 이동 수단
        params.put("transport_type", user.getTransportType().getType());
        // 내일 날짜
        params.put("tomorrow_day", tomorrow.toString());
        // 날씨
        params.put("tomorrow_weather", weather);

        return params;
    }
}
