package com.example.first.service;

import com.example.first.dto.UserDto;
import com.example.first.dto.UserRequestDto;
import com.example.first.entity.User;
import com.example.first.mapper.HomeMapper;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final HomeMapper homeMapper;
    private final SqlSessionTemplate sqlSessionTemplate;
//    private final BCryptPasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(HomeMapper homeMapper, SqlSessionTemplate sqlSessionTemplate) {
        this.homeMapper = homeMapper;
        this.sqlSessionTemplate = sqlSessionTemplate;
    }


    @Override
    public void signUp(UserDto userDto) {
        homeMapper.signUp(userDto);
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
