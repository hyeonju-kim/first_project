package com.example.first.service;

import com.example.first.dto.PasswordDto;
import com.example.first.dto.UserDto;
import com.example.first.exception.UserException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
    // 회원가입
    UserDto signUp(UserDto userDto) throws UserException;

    // 프로필 사진 업로드
    String storeProfilePicture(MultipartFile profilePicture, String fileName, String username, String originalName) throws IOException;

    //이메일 인증
    void sendAuthNumToEmail(String username);

    // 비번 찾기
    void sendTempPwToEmail(String username);

    // 비번 변경
    void changePw(PasswordDto passwordDto) throws UserException;

    // 로그인
    UserDetails login(UserDto userDto) throws UserException;

    // 마이페이지 화면 보이기
//    UserDto getUserInfo();

    // 개인정보 확인
//    UserResponseDto personalInfo();
    // 계정 중복확인

//    boolean isUsernameUnique(String username);
}
