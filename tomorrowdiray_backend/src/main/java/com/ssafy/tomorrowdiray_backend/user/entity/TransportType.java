package com.ssafy.tomorrowdiray_backend.user.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class TransportType {
    private Long id;
    private String type;

    @Builder
    public TransportType(Long id, String type) {
        this.id = id;
        this.type = type;
    }
}
