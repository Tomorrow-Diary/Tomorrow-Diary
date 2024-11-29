package com.ssafy.tomorrowdiray_backend.user.dto.response;

import com.ssafy.tomorrowdiray_backend.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupResponse {
    private String nickname;

    @Builder
    public SignupResponse(String nickname) {
        this.nickname = nickname;
    }

    public static SignupResponse toDto(User user) {
        return SignupResponse.builder()
                .nickname(user.getNickname())
                .build();
    }
}
