package com.example.first.dto;

import lombok.Data;

@Data
public class PasswordDto {
    private String password;
    private String newPassword;
    private String username;
}
