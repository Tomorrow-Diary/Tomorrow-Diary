package com.ssafy.tomorrowdiray_backend.user.service;

import com.ssafy.tomorrowdiray_backend.user.dto.SocialUser;
import com.ssafy.tomorrowdiray_backend.user.dto.request.LoginRequest;

public interface LoginService {
	SocialUser login(LoginRequest request);
}
