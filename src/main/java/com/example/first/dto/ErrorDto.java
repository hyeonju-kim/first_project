package com.example.first.dto;

import lombok.Data;

@Data
public class ErrorDto {

    private Long seq;
    private String username;
    private String nickname;
    private String errorTime;
    private String errorType;
    private String errorMessage;

    public ErrorDto(String username, String nickname, String errorTime, String errorType, String errorMessage) {
        this.username = username;
        this.nickname = nickname;
        this.errorTime = errorTime;
        this.errorType = errorType;
        this.errorMessage = errorMessage;
    }
}
