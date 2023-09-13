package com.example.first.service;

import com.example.first.dto.UserDto;
import com.example.first.dto.UserRequestDto;
import com.example.first.dto.UserResponseDto;
import org.springframework.stereotype.Service;

public interface UserService {
    // 회원가입
//    UserDto signUp(UserRequestDto userRequestDto);

    void signUp(UserDto userDto);

    //이메일 인증
    void sendEmail(String email);

//    // 로그인
//    void login(UserDto userDto);

    // 개인정보 확인
//    UserResponseDto personalInfo();
    // 계정 중복확인

    boolean isUsernameUnique(String username);
}
