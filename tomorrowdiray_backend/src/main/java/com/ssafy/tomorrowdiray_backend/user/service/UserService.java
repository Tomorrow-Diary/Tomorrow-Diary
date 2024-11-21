package com.ssafy.tomorrowdiray_backend.user.service;

import com.ssafy.tomorrowdiray_backend.user.dto.request.SignupRequest;
import com.ssafy.tomorrowdiray_backend.user.dto.response.SignupResponse;
import com.ssafy.tomorrowdiray_backend.user.entity.User;
import com.ssafy.tomorrowdiray_backend.user.entity.UserDestination;
import com.ssafy.tomorrowdiray_backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User signup(Long socialId, String nickname, SignupRequest request) {
        UserDestination userDestination = registDestination(request.getRoadAddress(), request.getLatitude(), request.getLongitude());

        User user = User.builder()
                .socialId(socialId)
                .nickname(nickname)
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .transportType(request.getRouteType())
                .userDestination(userDestination)
                .build();

        userRepository.insert(user);

        return user;
    }

    @Transactional
    public UserDestination registDestination(String roadAddress, Double latitude, Double longitude) {
        UserDestination userDestination = UserDestination.toEntity(roadAddress, latitude, longitude);
        userRepository.insertDestination(userDestination);
        return userDestination;
    }
}
