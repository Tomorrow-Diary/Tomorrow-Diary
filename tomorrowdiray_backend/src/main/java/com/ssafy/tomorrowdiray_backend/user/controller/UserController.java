package com.ssafy.tomorrowdiray_backend.user.controller;

import com.ssafy.tomorrowdiray_backend.global.exception.StatusCode;
import com.ssafy.tomorrowdiray_backend.global.response.BaseApiResponse;
import com.ssafy.tomorrowdiray_backend.user.dto.request.SignupRequest;
import com.ssafy.tomorrowdiray_backend.user.dto.response.SignupResponse;
import com.ssafy.tomorrowdiray_backend.user.entity.User;
import com.ssafy.tomorrowdiray_backend.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @Operation(
            summary = "회원가입 API",
            description = "OAuth 로그인 중 우리 서비스 사용자가 아닌 경우 회원가입을 요청합니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "회원가입 성공",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = SignupResponse.class)
            )
    )
    @PostMapping("/sign-up")
    public ResponseEntity<BaseApiResponse<SignupResponse>> signup(
            @Parameter(hidden = true) @SessionAttribute User user,
            @RequestBody SignupRequest request) {
        SignupResponse response = userService.signup(user, request);
        return BaseApiResponse.success(StatusCode.SIGNUP_SUCCESS, response);
    }


}
