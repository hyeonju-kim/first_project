package com.example.first.controller;

import com.example.first.dto.TestDto;
import com.example.first.dto.UserDto;
import com.example.first.dto.UserRequestDto;
import com.example.first.service.UserService;
import com.example.first.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Controller
@Slf4j
public class HomeController {
    private final UserService userService;
//    private final BCryptPasswordEncoder encoder;


    // 1. 회원가입 화면
    @GetMapping("/register")
    public String signupForm() {
        return "register";
    }

    // 2. 회원가입
    @PostMapping("/register")
    @ResponseBody
    public void signup(@RequestBody UserDto userDto) {
        System.out.println("TEST");
//        System.out.println(map.get("username"));
        System.out.printf("testDto == " + userDto.getEmail());

//        Map<String,Object> resMap = new HashMap<>();
        boolean success = true;
        try{
            // 회원가입 실시!
            userService.signUp(userDto);

        }catch (Exception e){
            e.printStackTrace();
//            resMap.put("msg" , "오류가 발생");
            userDto.setMessage("오류가 발생 ㅠㅠ ");
            success = false;
        }
        if(success){
//            resMap.put("url" , "home");
        }
//        resMap.put("success" , success);

//        return resMap;
        return ;
    }

//    @PostMapping("/register")
//    public String signup(@RequestParam Long userId,
//                         @RequestParam String username,
//                         @RequestParam String email,
//                         @RequestParam String nickname,
//                         @RequestParam String password,
//                         @RequestParam String phoneNumber) {
//        userService.signUp(userRequestDto);
//        return "home";
//    }

    // 3. 로그인 화면
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    // 4. 로그인
    @PostMapping("/login")
    public String login(UserRequestDto userRequestDto) {
        userService.login(userRequestDto);
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

//    // 😊 1. 로그인
//    @PostMapping("/login")
//    public ResponseEntity<UserResponseDto> loginTest(@RequestBody UserRequestDto userRequestDto) throws UserException {
//
//
//        String password = userRequestDto.getPassword();
//        String username = userRequestDto.getUsername();
//        User retrievedUser = userRequestDto.findByUsername(username);
//
//
//        if (retrievedUser == null) {
//            throw new UserException("가입되지 않은 이메일입니다.", HttpStatus.BAD_REQUEST, null);
//        }
//        String nickname = retrievedUser.getNickname();
//        String phoneNumber = retrievedUser.getPhoneNumber();
//
//
//        // 조회한 비밀번호
//        String foundPw = retrievedUser.getPassword();
//        log.info("foundPw = {}", foundPw);
//
//        //비밀번호 같은지 여부 파악
//        if (!encoder.matches(password, foundPw)) {
//            throw new UserException("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST, null);
//        }
//
//
//        // 2. 인증 성공(회원저장소에 해당 이름이 있으면) 후 인증된 user의 정보를 갖고옴
//        UserDetails userDetails = userDetailsService.loadUserByUsername(userRequestDto.getUsername());
//
//        // 3. subject, claim 모두 UserDetails를 사용하므로 객체를 그대로 전달
//        String accessToken = jwtUtil.generateAccessToken(userDetails);
//        String refreshToken = jwtUtil.generateRefreshToken(userDetails); // 230814 추가
//
//
//
//
//        // 4. 생성된 토큰을 응답
//        UserResponseDto loginSuccessResponseDto = new UserResponseDto(accessToken);
//
//        return ResponseEntity.ok(loginSuccessResponseDto);
//
//        return ResponseEntity.ok(new UserResponseDto(password +"  +  " + username));
//    }





//    // 😊 2. 회원가입
//    @PostMapping("/signup")
//    public ResponseEntity<String> signUp(@RequestBody UserRequestDto userRequestDto) {
//
////        UserDto userDto = userService.signUp(userRequestDto);
//        userService.signUp(userRequestDto);
////        UserResponseDto userResponseDto = userDto.convertToUserResponseDto();
////            userResponseDto.setMessage("회원가입이 완료되었습니다.");
////        return ResponseEntity.ok(userResponseDto);
//        return ResponseEntity.ok("회원가입이 완료되었습니다.");
//    }
//
//    // 유저네임(이메일) 중복확인 - 230901 추가
//    @GetMapping("/check-username/{username}")
//    public ResponseEntity<UserResponseDto> checkUsername(@PathVariable String username) {
//        boolean isUsernameUnique = userService.isUsernameUnique(username);
//        if (isUsernameUnique) {
//            return ResponseEntity.ok(new UserResponseDto("이미 사용 중인 이메일 주소입니다.", "error"));
//        }else {
//            return ResponseEntity.ok(new UserResponseDto("사용 가능한 이메일 주소 입니다.", "success"));
//        }
//    }



    // 😊 3. 비밀번호 찾기 - 230907 추가
//    @PostMapping("/forgot-password")
//    public ResponseEntity<UserResponseDto> sendForgotPasswordEmail(@RequestBody UserRequestDto userRequestDto){
//        // 임시 유저 정보 생성하고 메일 발송
//        boolean authInfo = authService.saveTempAuthInfo(userRequestDto.getUsername());
//        return ResponseEntity.ok(new UserResponseDto("메일을 발송 했습니다.", "success"));
//    }


}
