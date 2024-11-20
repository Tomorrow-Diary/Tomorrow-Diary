package com.ssafy.tomorrowdiray_backend.user.service;

import com.ssafy.tomorrowdiray_backend.user.dto.request.SignupRequest;
import com.ssafy.tomorrowdiray_backend.user.dto.response.SignupResponse;
import com.ssafy.tomorrowdiray_backend.user.entity.User;
import com.ssafy.tomorrowdiray_backend.user.entity.UserDestination;
import com.ssafy.tomorrowdiray_backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public SignupResponse signup(User user, SignupRequest request) {

        Long socialId = user.getSocialId();
        String nickname = user.getNickname();

        LocalTime startTime = request.getStartTime();
        LocalTime endTime = request.getEndTime();
        Long transportTypeId = request.getRouteType().getOrder();
        Long usersDestinationId = registDestination(request.getRoadAddress(), request.getLatitude(), request.getLongitude());

        userRepository.insert(socialId, nickname, startTime, endTime, transportTypeId, usersDestinationId);

        return SignupResponse.toDto(nickname);
    }

    public Long registDestination(String roadAddress, double latitude, double longitude) {
        return userRepository.insertDestination(
                UserDestination.builder()
                        .destinationAddress(roadAddress)
                        .latitude(latitude)
                        .longitude(longitude)
                        .build());
    }
}
