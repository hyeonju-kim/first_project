package com.example.first.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserResponseDto{

    private String message;

    private Long userId;


    private String username;

    private String nickname;

    private String phoneNumber;

    private String email;



    public UserResponseDto(String message) {
        this.message = message;
    }

    public UserResponseDto(String message, String status) {
        this.message = message;
    }


    public UserResponseDto(String username, String nickname, String phoneNumber) {
        this.username = username;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
    }

    public static UserResponseDto createEmpty() {
        return new UserResponseDto(); // 빈 객체 반환
    }

}
