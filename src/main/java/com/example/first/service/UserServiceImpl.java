package com.example.first.service;

import com.example.first.dto.TempAuthInfo;
import com.example.first.dto.UserDto;
import com.example.first.dto.UserRequestDto;
import com.example.first.entity.User;
import com.example.first.mapper.HomeMapper;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final ApplicationEventPublisher eventPublisher;
    private final HomeMapper homeMapper;
    private static final String NUMBERS = "0123456789";
    private static final Random RANDOM = new Random();


    // 회원가입
    @Override
    public void signUp(UserDto userDto) {
        homeMapper.signUp(userDto);
    }

    // 회원가입 시 6자리 인증번호 생성
    public static String generatorAuthNumber() {
        StringBuilder password = new StringBuilder();

        for(int i = 0; i < 6; i++) { // 항상 6자리 숫자를 생성
            int randomIndex = RANDOM.nextInt(NUMBERS.length());
            char randomChar = NUMBERS.charAt(randomIndex);
            password.append(randomChar);
        }
        return password.toString();
    }

    @Override
    public void sendEmail(String email) {
        // 인증번호 생성
        String authNumber = generatorAuthNumber();

        // 임시 유저 정보 생성
        TempAuthInfo tempAuthInfo = new TempAuthInfo();
        tempAuthInfo.setEmail(email);
        tempAuthInfo.setAuthNumber(authNumber);

        // 디비에 인증번호 저장
        homeMapper.setAuth(tempAuthInfo);

        // 메일 전송 이벤트 퍼블리싱(비동기)
        eventPublisher.publishEvent(tempAuthInfo);
    }

//    @Override
//    public void login(UserDto userDto) {
//        Long userId = userDto.getUserId();
//        User foundUser = homeMapper.findByUserId(userId);
//        if (userId == foundUser.getUserId()) {
//
//            homeMapper.login(userDto);
//        }
//
//    }

//    @Override
//    public UserResponseDto personalInfo() {
//        return homeMapper.personalInfo();
//    }

    @Override
    public boolean isUsernameUnique(String username) {
        return homeMapper.isUsernameUnique(username);
    }
}
