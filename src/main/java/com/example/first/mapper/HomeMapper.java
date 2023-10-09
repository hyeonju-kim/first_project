package com.example.first.mapper;

import com.example.first.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface HomeMapper {
    // 회원가입
    UserDto signUp(UserDto userDto);

    // 프로필 사진 업로드
    void storeProfilePicture(ProfilePicture picture);

    // 사용자 이메일로 사용자 가져오는 메서드
    UserDto findByUsername(String username);

    // username 으로 인증번호 가져오기
    TempAuthInfo findAuthNumberByUsername(String username);


    // 로그인
//    UserDto login(UserDto userDto);

    // 마이페이지 정보 가져오기
    UserDto getUserInfo(String username);

    // 비밀번호 변경
    void changePw(PasswordDto passwordDto);


    // 메일로 인증번호 발송시 auth 테이블에 이메일, 인증번호 저장
    void setAuth(TempAuthInfo tempAuthInfo);

    // 사용자아이디로 사용자 가져오는 메서드
    UserDto findByUserId(Long userId);

    // username 으로 프로필사진 원본이름 가져오기
    String findProfilePictureOriginalName(String username);

    // username 으로 프로필사진 경로 가져오기
    String findProfilePictureSavePath(String username);

    // username 으로 프로필사진 파일명 가져오기
    String findProfilePictureFileName(String username);

    // 프로필 사진 제일 최근 객체 가져오기
    ProfilePicture getRecentProfilePicture();

    // 회원가입 시 프로필사진 가져와서 username 업데이트
    void updateProfilePicture(UserDto userDto);

    // 마이페이지 접근 시 , 유저 객체 가져와서 save_path 업데이트
    void updateUserInsertSavePath(Map map);


    // 개인정보 확인
//    UserResponseDto personalInfo();

    // 계정 중복확인
//    boolean isUsernameUnique(String username);

    // 모든 유저 객체 조회 (스케줄링 용)
    List<UserDto> findAllUsers();

    // 마지막 로그인 시간을 업데이트
    void updateUserLastLoginDate(UserDto userDto);

    // 유저의 휴면처리
    void updateUserStatusToN(UserDto userDto);

    // 에러 인서트
    void insertError(ErrorDto errorDto);

}
