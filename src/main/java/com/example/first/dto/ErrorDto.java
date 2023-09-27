package com.example.first.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorDto {

    private Long seq;
    private String username;
    private String nickname;
    private LocalDateTime errorTime;
    private String errorType;
    private String errorMessage;

    public ErrorDto(String username, String nickname, LocalDateTime errorTime, String errorType, String errorMessage) {
        this.username = username;
        this.nickname = nickname;
        this.errorTime = errorTime;
        this.errorType = errorType;
        this.errorMessage = errorMessage;
    }
}
