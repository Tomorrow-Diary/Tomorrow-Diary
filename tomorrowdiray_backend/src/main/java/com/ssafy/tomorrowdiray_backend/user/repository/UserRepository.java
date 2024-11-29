package com.ssafy.tomorrowdiray_backend.user.repository;


import com.ssafy.tomorrowdiray_backend.user.dto.SocialUser;
import com.ssafy.tomorrowdiray_backend.user.entity.User;
import com.ssafy.tomorrowdiray_backend.user.entity.UserDestination;

import java.util.Optional;

public interface UserRepository {

    Long insertDestination(UserDestination userDestination);

	Optional<User> findBySocialUser(SocialUser socialUser);

    int insert(User user);
}
