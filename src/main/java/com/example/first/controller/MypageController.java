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
        // 사용자 정보를 가져와 모델에 추가
//        UserDto userDto = userService.getUserInfo(); // UserService에서 현재 사용자 정보를 가져오는 메서드


//        UserDto user = homeMapper.findByUsername("akak111@naver.com");

        // 현재 사용자의 인증 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("마이페이지 - authentication = " + authentication);

        // 사용자 이름 가져오기
        String username = authentication.getName();

        UserDto foundUser = homeMapper.findByUsername(username);

        model.addAttribute("username", foundUser.getUsername());
        model.addAttribute("nickname", foundUser.getNickname());
        model.addAttribute("phoneNumber", foundUser.getPhoneNumber());
        model.addAttribute("streetAdr", foundUser.getStreetAdr());
        model.addAttribute("detailAdr", foundUser.getDetailAdr());




        System.out.println("마이페이지 - authentication.getName() ==== " + authentication.getName());
        System.out.println("마이페이지 - authentication.getCredentials() ==== " + authentication.getCredentials());
        System.out.println("마이페이지 - authentication.getDetails() ==== " + authentication.getDetails());
        System.out.println("마이페이지 - authentication.getPrincipal() ==== " + authentication.getPrincipal());
        System.out.println("마이페이지 - authentication.getAuthorities() ==== " + authentication.getAuthorities());
        System.out.println("마이페이지 - authentication==== " + authentication);

        // 여기에서 필요한 정보를 세션에서 가져와 모델에 추가하거나 직접 사용할 수 있습니다.
        // 예를 들어, 사용자 이름을 모델에 추가하면 해당 정보를 뷰에서 사용할 수 있습니다.
//        model.addAttribute("username", username);

        return "mypage";
    }


}
