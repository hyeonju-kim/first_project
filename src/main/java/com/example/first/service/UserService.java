package com.example.first.service;

import com.example.first.dto.UserDto;
import com.example.first.exception.UserException;

public interface UserService {
    // 회원가입
//    UserDto signUp(UserRequestDto userRequestDto);

    UserDto signUp(UserDto userDto) throws UserException;

    //이메일 인증
    void sendAuthNumToEmail(String username);

    // 비번 찾기
    void sendTempPwToEmail(String username);

    // 로그인
    UserDto login(UserDto userDto) throws UserException;

    // 마이페이지 화면 보이기
    UserDto getUserInfo();

    // 개인정보 확인
//    UserResponseDto personalInfo();
    // 계정 중복확인

    boolean isUsernameUnique(String username);
}
