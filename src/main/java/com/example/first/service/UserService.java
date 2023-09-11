package com.example.first.service;

import com.example.first.dto.UserDto;
import com.example.first.dto.UserRequestDto;
import com.example.first.dto.UserResponseDto;

public interface UserService {


    // 회원가입
//    UserDto signUp(UserRequestDto userRequestDto);

    void signUp(UserRequestDto userRequestDto);

    // 개인정보 확인
//    UserResponseDto personalInfo();

    // 계정 중복확인
    boolean isUsernameUnique(String username);

}
