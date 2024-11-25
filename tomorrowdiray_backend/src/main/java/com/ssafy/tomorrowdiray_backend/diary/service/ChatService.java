package com.ssafy.tomorrowdiray_backend.diary.service;

import com.ssafy.tomorrowdiray_backend.diary.dto.request.DiaryRequest;
import com.ssafy.tomorrowdiray_backend.facilities.entity.Facility;
import com.ssafy.tomorrowdiray_backend.global.util.JsonUtil;
import com.ssafy.tomorrowdiray_backend.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class ChatService {
    private final ChatClient chatClient;

    @Value("classpath:/prompt/diary-create.st")
    private Resource diaryCreatePrompt;

    public ChatService(@Qualifier("openAiChatModel") ChatModel chatModel) {
        this.chatClient = ChatClient.create(chatModel);
    }

    public String generateDiaryContent(DiaryRequest request, User user, Facility firstFacility, Facility secondFacility, String weather, LocalDate tomorrow) {
        Map<String, Object> params = makeAIParams(request, user, firstFacility, secondFacility, weather, tomorrow);

        String content = chatClient.prompt()
                .user(userSpec -> userSpec
                        .text(diaryCreatePrompt)
                        .params(params))
                .call()
                .content();

        return JsonUtil.cleanUpJson(content);
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

