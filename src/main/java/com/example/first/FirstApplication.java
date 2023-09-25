package com.example.first;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan("com.example.first.mapper") // MyBatis Mapper 인터페이스의 패키지 위치를 지정
@SpringBootApplication
@EnableScheduling // 스케줄링 처리 위해 추가
public class FirstApplication {

    public static void main(String[] args) {
        SpringApplication.run(FirstApplication.class, args);
    }

}
