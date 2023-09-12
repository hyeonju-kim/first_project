package com.example.first.mapper;

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
    UserDto signUp(UserRequestDto userRequestDto);

    // 로그인
    void login(UserRequestDto userRequestDto);


    // 사용자아이디로 사용자 가져오는 메서드
    User findByUserId(Long userId);

//    // 개인정보 확인
//    UserResponseDto personalInfo();

    // 계정 중복확인
    boolean isUsernameUnique(String username);

}
