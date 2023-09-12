package com.example.first.service;

import com.example.first.dto.UserDto;
import com.example.first.dto.UserRequestDto;
import com.example.first.dto.UserResponseDto;
import com.example.first.entity.User;
import com.example.first.mapper.HomeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final HomeMapper homeMapper;
//    private final BCryptPasswordEncoder encoder;

    @Override
    public void signUp(UserRequestDto userRequestDto) {
        homeMapper.signUp(userRequestDto);
    }

    @Override
    public void login(UserRequestDto userRequestDto) {
        Long userId = userRequestDto.getUserId();
        User foundUser = homeMapper.findByUserId(userId);
        if (userId == foundUser.getUserId()) {

            homeMapper.login(userRequestDto);
        }

    }

//    @Override
//    public UserResponseDto personalInfo() {
//        return homeMapper.personalInfo();
//    }

    @Override
    public boolean isUsernameUnique(String username) {
        return homeMapper.isUsernameUnique(username);
    }
}
