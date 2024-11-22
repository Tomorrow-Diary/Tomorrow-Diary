package com.ssafy.tomorrowdiray_backend.diary.service;

import com.ssafy.tomorrowdiray_backend.diary.dto.request.DiaryRequest;
import com.ssafy.tomorrowdiray_backend.user.service.UserService;
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
    private final UserService userService;

    @Value("classpath:/prompt/diary-create.st")
    private Resource diaryCreate;

    public DiaryService(@Qualifier("openAiChatModel") ChatModel chatModel, UserService userService) {
        chatClient = ChatClient.create(chatModel);
        this.userService = userService;
    }

    public String createDiary(DiaryRequest request) {




        String content = chatClient.prompt()
                .user(userSpec -> userSpec
                        .text(diaryCreate)
                        .param("adjective", "funny")
                        .param("topic", "cows"))
                .call()
                .content();
        log.debug("content: {}", content);
        return content;
    }
}
