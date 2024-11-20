package com.ssafy.tomorrowdiray_backend.user.dto.response;

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

    public static SignupResponse toDto(String nickname) {
        return SignupResponse.builder()
                .nickname(nickname)
                .build();
    }
}
