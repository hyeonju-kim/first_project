package com.example.first.service;

import com.example.first.dto.PasswordDto;
import com.example.first.dto.TempAuthInfo;
import com.example.first.dto.UserDto;
import com.example.first.exception.UserException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
    // 🔥 1. 회원가입
    UserDto signUp(UserDto userDto) throws UserException;

    // 🔥 4. 프로필 사진 경로 반환 및 업로드
    String storeProfilePicture(MultipartFile profilePicture, String fileName, String username, String originalName) throws IOException;

    // 🔥 7. 회원가입 시 메일로 인증번호 발송
    void sendAuthNumToEmail(TempAuthInfo tempAuthInfo);

    // 🔥 9. 비밀번호 찾기 - 메일로 임시 비밀번호 전송
    void sendTempPwToEmail(String username);

    // 🔥 10. 비밀번호 변경
    void changePw(PasswordDto passwordDto) throws UserException;

    // 🔥 11. 로그인
    UserDetails login(UserDto userDto) throws UserException;

    // 🔥 12. 마이페이지 화면 보이기
//    UserDto getUserInfo();

    // 개인정보 확인
//    UserResponseDto personalInfo();
    // 계정 중복확인

//    boolean isUsernameUnique(String username);
}
