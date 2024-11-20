package com.ssafy.tomorrowdiray_backend.global.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.ssafy.tomorrowdiray_backend")
public class MyBatisConfig {
}

