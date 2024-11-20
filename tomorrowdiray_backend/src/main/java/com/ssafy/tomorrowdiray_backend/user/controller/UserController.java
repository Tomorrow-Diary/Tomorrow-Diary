package com.ssafy.tomorrowdiray_backend.user.controller;

import com.ssafy.tomorrowdiray_backend.global.exception.StatusCode;
import com.ssafy.tomorrowdiray_backend.global.response.BaseApiResponse;
import com.ssafy.tomorrowdiray_backend.user.dto.SocialUser;
import com.ssafy.tomorrowdiray_backend.user.dto.request.LoginRequest;
import com.ssafy.tomorrowdiray_backend.user.dto.request.SignupRequest;
import com.ssafy.tomorrowdiray_backend.user.dto.response.LoginResponse;
import com.ssafy.tomorrowdiray_backend.user.dto.response.SignupResponse;
import com.ssafy.tomorrowdiray_backend.user.service.KakaoLoginService;
import com.ssafy.tomorrowdiray_backend.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;
    private final KakaoLoginService kakaoLoginService;

    @Operation(summary = "회원가입 API", description = "OAuth 로그인 중 우리 서비스 사용자가 아닌 경우 회원가입을 요청합니다.")
    @ApiResponse(responseCode = "200", description = "회원가입 성공")
    @PostMapping("/sign-up")
    public ResponseEntity<BaseApiResponse<SignupResponse>> signup(
            @Parameter(hidden = true) @SessionAttribute Long socialId,
            @Parameter(hidden = true) @SessionAttribute String nickname,
            @RequestBody SignupRequest request) {
        SignupResponse response = userService.signup(socialId, nickname, request);
        return BaseApiResponse.success(StatusCode.SIGNUP_SUCCESS, response);
    }

    @Operation(summary = "Kakao 로그인 API", description = "카카오 로그인을 진행합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "로그인 성공"),
        @ApiResponse(responseCode = "202", description = "회원가입이 필요합니다.")
    })
    @PostMapping("/kakao-login")
    public ResponseEntity<BaseApiResponse<Object>> kakaoLogin(
        HttpSession session,
        HttpServletResponse response,
        @RequestBody LoginRequest request) {
        SocialUser socialUser = kakaoLoginService.login(request);

        // 회원가입이 필요한 유저
        if(!socialUser.isExistUser()) {
            session.setAttribute("socialId", socialUser.getSocialId());
            session.setAttribute("nickname", socialUser.getNickname());

            return BaseApiResponse.success(StatusCode.REQUIRED_SIGNUP);
        }

        // 로그인 성공 유저
        session.setAttribute("user", socialUser.getUser());
        Cookie sessionIdCookie = makeCookie("SESSIONID", session.getId()); // 세션 ID 쿠키 생성
        response.addCookie(sessionIdCookie);
        LoginResponse loginResponse = LoginResponse.toDto(socialUser.getNickname());
        return BaseApiResponse.success(StatusCode.LOGIN_SUCCESS, loginResponse);
    }

    private Cookie makeCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60 * 60);
        return cookie;
    }
}
