package com.ssafy.tomorrowdiray_backend.diary.entity;

import com.ssafy.tomorrowdiray_backend.house.entity.House;
import com.ssafy.tomorrowdiray_backend.user.entity.User;
import lombok.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Diary {
    private Long id;
    private LocalDate date;
    private String weather;
    private User user;
    private House house;

    @Builder
    public Diary(Long id, LocalDate date, String weather, User user, House house) {
        this.id = id;
        this.date = date;
        this.weather = weather;
        this.user = user;
        this.house = house;
    }

    public static Diary toEntity(LocalDate date, String weather, User user, House house) {
        return Diary.builder()
                .date(date)
                .weather(weather)
                .user(user)
                .house(house)
                .build();
    }
}
