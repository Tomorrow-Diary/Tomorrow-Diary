package com.ssafy.tomorrowdiray_backend.address.repository;

import com.ssafy.tomorrowdiray_backend.address.entity.Address;

import java.util.List;

public interface AddressRepository {

    List<Address> selectBykeyword(String keyword);
}
