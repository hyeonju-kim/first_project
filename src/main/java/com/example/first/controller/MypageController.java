package com.example.first.controller;


import com.example.first.dto.UserDto;
import com.example.first.mapper.HomeMapper;
import com.example.first.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Controller
@Slf4j
@CrossOrigin
public class MypageController {
    private final UserService userService;
    private final HomeMapper homeMapper;

    // 마이페이지 화면
    @GetMapping("/mypage")
    public String mypage(Model model) {
        // 현재 사용자의 인증 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("마이페이지 - authentication = " + authentication);

        // 사용자 이름 가져오기
        String username = authentication.getName();
        UserDto userDto = homeMapper.findByUsername(username);

        System.out.println("마이페이지 - foundUser.getUsername() ==== " + userDto.getUsername());
        System.out.println("마이페이지 - foundUser.getName() ==== " + userDto.getName());
        System.out.println("마이페이지 - foundUser.getNickname() ==== " + userDto.getNickname());
        System.out.println("마이페이지 - foundUser.getPhoneNumber() ==== " + userDto.getPhoneNumber());
        System.out.println("마이페이지 - foundUser.getStreetAdr() ==== " + userDto.getStreetAdr());
        System.out.println("마이페이지 - foundUser.getDetailAdr() ==== " + userDto.getDetailAdr());


        // 프로필 사진 경로 가져와서 저장하기 (미리 저장 못해서 일단 이렇게 가져와서 넣어주자,,)
        String profilePictureSavePath = homeMapper.findProfilePictureSavePath(username);// 왜 여기서 못가져오지?

        System.out.println("마이페이지 컨트롤러 / 화면 - profilePictureSavePath =  " + profilePictureSavePath);
        userDto.setProfilePictureLocation(profilePictureSavePath);


        Map<String, Object> params = new HashMap<>();
        params.put("savePath", profilePictureSavePath);
        params.put("userDto", userDto);


        homeMapper.updateUserInsertSavePath(params);

        model.addAttribute("user", userDto);

        return "mypage";
    }
}
