package com.ssafy.tomorrowdiray_backend.diary.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Getter
@RequiredArgsConstructor
public enum Weather {
    SUNNY("맑음"),
    CLOUDY("흐림"),
    RAINY("비"),
    SNOWY("눈");

    private final String description;

    public static String getRandomWeather() {
        LocalDate now = LocalDate.now();

        List<Weather> possibleWeathers;
        if (isWinter(now)) {
            possibleWeathers = List.of(SUNNY, CLOUDY, RAINY, SNOWY);
        } else {
            possibleWeathers = List.of(SUNNY, CLOUDY, RAINY);
        }

        int randomIndex = ThreadLocalRandom.current().nextInt(possibleWeathers.size());
        return possibleWeathers.get(randomIndex).description;
    }

    private static boolean isWinter(LocalDate date) {
        int month = date.getMonthValue();
        return month == 12 || month == 1 || month == 2; // 12월, 1월, 2월을 겨울로 간주
    }
}
