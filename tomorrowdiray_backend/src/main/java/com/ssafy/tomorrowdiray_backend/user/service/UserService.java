package com.ssafy.tomorrowdiray_backend.user.service;

import com.ssafy.tomorrowdiray_backend.user.dto.request.SignupRequest;
import com.ssafy.tomorrowdiray_backend.user.entity.TransportType;
import com.ssafy.tomorrowdiray_backend.user.entity.User;
import com.ssafy.tomorrowdiray_backend.user.entity.UserDestination;
import com.ssafy.tomorrowdiray_backend.user.repository.TransportTypeRepository;
import com.ssafy.tomorrowdiray_backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TransportTypeRepository transportTypeRepository;

    @Transactional
    public User signup(Long socialId, String nickname, SignupRequest request) {
        UserDestination userDestination = registDestination(request.getRoadAddress(), request.getLatitude(), request.getLongitude());
        TransportType transportType = transportTypeRepository.findByName(request.getTransportType());

        User user = User.toEntity(socialId, nickname, request.getStartTime(), request.getEndTime(), transportType, userDestination);

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
