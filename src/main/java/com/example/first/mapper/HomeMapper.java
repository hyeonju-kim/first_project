package com.example.first.mapper;

import com.example.first.dto.TempAuthInfo;
import com.example.first.dto.UserDto;
import com.example.first.dto.UserRequestDto;
import com.example.first.dto.UserResponseDto;
import com.example.first.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface HomeMapper {
    // 회원가입
    void signUp(UserDto userDto);

//    // 로그인
//    void login(UserDto userDto);


    // 메일로 인증번호 발송시 auth 테이블에 이메일, 인증번호 저장
    void setAuth(TempAuthInfo tempAuthInfo);

    // 사용자아이디로 사용자 가져오는 메서드
    UserDto findByUserId(Long userId);

    // 사용자 이메일로 사용자 가져오는 메서드
    User findByUserEmail(String email);

//    // 개인정보 확인
//    UserResponseDto personalInfo();

    // 계정 중복확인
    boolean isUsernameUnique(String username);

}
