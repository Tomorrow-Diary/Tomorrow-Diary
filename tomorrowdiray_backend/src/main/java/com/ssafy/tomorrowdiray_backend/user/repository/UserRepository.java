package com.ssafy.tomorrowdiray_backend.user.repository;


import com.ssafy.tomorrowdiray_backend.user.entity.UserDestination;

import java.time.LocalTime;

public interface UserRepository {

    Long insertDestination(UserDestination userDestination);

    void insert(Long socialId, String nickname, LocalTime startTime, LocalTime endTime, Long transportTypeId, Long usersDestinationId);
}
