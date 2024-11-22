package com.ssafy.tomorrowdiray_backend.user.repository;

import com.ssafy.tomorrowdiray_backend.user.entity.TransportType;

public interface TransportTypeRepository {
    TransportType findByName(String type);
}
