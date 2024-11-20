package com.ssafy.tomorrowdiray_backend.user.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.ssafy.tomorrowdiray_backend.global.exception.ErrorCode;
import com.ssafy.tomorrowdiray_backend.user.dto.SocialUser;
import com.ssafy.tomorrowdiray_backend.user.dto.request.LoginRequest;
import com.ssafy.tomorrowdiray_backend.user.entity.User;
import com.ssafy.tomorrowdiray_backend.user.exception.UserLoginException;
import com.ssafy.tomorrowdiray_backend.user.repository.UserRepository;

import io.netty.handler.codec.http.HttpHeaderValues;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class KakaoLoginService implements LoginService {

	private final static String KAUTH_TOKEN_URL_HOST ="https://kauth.kakao.com";
	private final static String KAUTH_USER_URL_HOST = "https://kapi.kakao.com";

	@Value("${kakao.client_id}")
	private String clientId;

	private final UserRepository repository;

	@Override
	public SocialUser login(LoginRequest request) {
		String accessToken = getAccessTokenFromKakao(request.getAuthorizationCode());
		SocialUser socialUser = getUserInfo(accessToken);

		Optional<User> user = repository.findBySocialUser(socialUser);

		if (user.isEmpty()) {
			return socialUser;
		}

		return SocialUser.builder()
			.user(user.get())
			.build();
	}

	public String getAccessTokenFromKakao(String code) {
		// Kakao API 호출
		Map<String, Object> response = WebClient.create(KAUTH_TOKEN_URL_HOST)
			.post()
			.uri(uriBuilder -> uriBuilder
				.path("/oauth/token")
				.queryParam("grant_type", "authorization_code")
				.queryParam("client_id", clientId)
				.queryParam("code", code)
				.build(true))
			.header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
			.retrieve()
			.bodyToMono(Map.class) // JSON 응답을 Map으로 변환
			.block();

		// access_token 추출
		if (response != null && response.containsKey("access_token")) {
			return response.get("access_token").toString();
		} else {
			throw new UserLoginException(ErrorCode.FAIL_GET_ACCESSTOKEN);
		}
	}


	private SocialUser getUserInfo(String accessToken) {
		Map<String, Object> response = WebClient.create(KAUTH_USER_URL_HOST)
			.get()
			.uri(uriBuilder -> uriBuilder
				.scheme("https")
				.path("/v2/user/me")
				.build(true))
			.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
			.header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
			.retrieve()
			.bodyToMono(Map.class)
			.block();

		// 필요한 데이터 추출
		Long socialId = Long.valueOf(response.get("id").toString()); // "id" 필드 추출
		Map<String, Object> kakaoAccount = (Map<String, Object>) response.get("kakao_account"); // "kakao_account" 추출
		Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile"); // "profile" 추출
		String nickname = profile.get("nickname").toString(); // "nickname" 추출

		// SocialUser 객체 생성
		return SocialUser.builder()
			.socialId(socialId)
			.nickname(nickname)
			.build();
	}
}
