package com.example.first.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY) //비어있지 않은 필드만 나타내는 어노테이션
public class User {

    private String message;

    private Long userId;


    private String username;

    private String nickname;

    private String phoneNumber;

    private String email;

    private String password;
}
