package com.example.first.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TempAuthInfo {
    private String username;
    private String authNumber;
    private LocalDateTime createdAt;
}
