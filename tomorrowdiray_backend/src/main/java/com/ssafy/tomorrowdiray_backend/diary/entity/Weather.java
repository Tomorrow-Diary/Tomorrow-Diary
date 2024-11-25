package com.ssafy.tomorrowdiray_backend.diary.entity;

import java.util.concurrent.ThreadLocalRandom;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Weather {
    SUNNY("맑음"),
    CLOUDY("흐림"),
    RAINY("비"),
    SNOWY("눈");

    private final String description;

    public static String getRandomWeather() {
        Weather[] values = Weather.values();
        int randomIndex = ThreadLocalRandom.current().nextInt(values.length);
        return values[randomIndex].description;
    }
}
