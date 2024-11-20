package com.ssafy.tomorrowdiray_backend.user.controller;

import com.ssafy.tomorrowdiray_backend.global.exception.StatusCode;
import com.ssafy.tomorrowdiray_backend.global.response.BaseApiResponse;
import com.ssafy.tomorrowdiray_backend.user.dto.request.SignupRequest;
import com.ssafy.tomorrowdiray_backend.user.dto.response.SignupResponse;
import com.ssafy.tomorrowdiray_backend.user.entity.User;
import com.ssafy.tomorrowdiray_backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<BaseApiResponse<SignupResponse>> signup(
            @SessionAttribute User user,
            @RequestBody SignupRequest request) {
        SignupResponse response = userService.signup(user, request);
        return BaseApiResponse.success(StatusCode.SIGNUP_SUCCESS, response);
    }


}
