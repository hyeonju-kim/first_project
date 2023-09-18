package com.example.first.controller;

import com.example.first.dto.PasswordDto;
import com.example.first.dto.TempAuthInfo;
import com.example.first.dto.UserDto;
import com.example.first.exception.ErrorBox;
import com.example.first.exception.UserException;
import com.example.first.mapper.HomeMapper;
import com.example.first.service.UserDetailsImpl;
import com.example.first.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@Controller
@Slf4j
@CrossOrigin
public class HomeController {
    private final UserService userService;
    private final HomeMapper homeMapper;
    private final AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;

    private UserDto user;

    // 홈 화면 (로그인/ 회원가입 버튼 있는)
    @GetMapping
    public String home() {
        return "home";
    }

    // 1. 회원가입 화면
    @GetMapping("/register")
    public String signupForm() {
        return "register";
    }

    // 2. 회원가입
    @PostMapping("/register")
    @ResponseBody
    public UserDto signup(@Valid @RequestBody UserDto userDto, BindingResult bindingResult) throws UserException, IOException {
        System.out.println("TEST");
        System.out.println("testDto == " + userDto.getUsername());
//        System.out.println("profilePicture == " + userDto.getProfilePicture());
//        System.out.println("streetAdr == " + userDto.getAddress().getStreetAdr());

        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            System.out.println("allErrors========== :  " + allErrors);
            String message = allErrors.get(0).getDefaultMessage();
            String code = allErrors.get(0).getCode();
            LocalDateTime now = LocalDateTime.now();
            String dateTime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            ErrorBox errorBox = new ErrorBox();
            errorBox.setCause(code);
            errorBox.setMessage("[ 고객 id ] : 해당 없음" + " | [ 에러 유형 ] : " + code + " | [ 에러 시간 ] : " + dateTime + " | [ 에러메시지 ] : " + message);
            log.error(errorBox.getMessage());
            throw new UserException(errorBox);
        }

        // 파일 업로드 처리
//        String fileName = userDto.getUsername() + "_" + System.currentTimeMillis() + "_" + userDto.getProfilePicture().getOriginalFilename();

        // 프로필 사진 업로드
//        String profilePictureLocation = userService.storeProfilePicture(userDto.getProfilePicture(), fileName);
//        userDto.setProfilePictureLocation(profilePictureLocation);

//        try{
//            // 회원가입 실시!
//            UserDto regiesteredUserDto = userService.signUp(userDto);
//        }catch (Exception e){
//            e.printStackTrace();
//            userDto.setMessage("오류가 발생 ㅠㅠ ");
//        }

        return userService.signUp(userDto);
    }

    // 파일 업로드
    @PostMapping("/upload-profilePicture")
    public ResponseEntity<String> uploadProfilePicture(@RequestParam("uploadFile") MultipartFile profilePicture) throws IOException {

        System.out.println("컨트롤러/파일업로드 - profilePicture = " + profilePicture);
        System.out.println("컨트롤러/파일업로드 - profilePicture.getName() = " + profilePicture.getName());

        // 내가 업로드 파일을 저장할 경로
        String fileName =  System.currentTimeMillis() + "_" + profilePicture.getOriginalFilename();

//        String profilePictureLocation = userService.storeProfilePicture(profilePicture, fileName);
//        userDto.setProfilePictureLocation(profilePictureLocation);


        String savePath =  "C:\\Program Files\\hj\\first_project\\profile_picture\\" + fileName;
        // 저장할 파일, 생성자로 경로와 이름을 지정해줌.
        File saveFile = new File(savePath, fileName);

        userService.storeProfilePicture(profilePicture, fileName);


        try {
            // void transferTo(File dest) throws IOException 업로드한 파일 데이터를 지정한 파일에 저장
            profilePicture.transferTo(saveFile);
            return ResponseEntity.ok("파일 업로드 성공");
        } catch (IOException e) {
            e.printStackTrace();
            // 파일 업로드 실패 처리를 여기에 추가할 수 있습니다.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 업로드 실패");
        }
    }

    // 3. 이메일로 인증번호 전송
    @ResponseBody
    @PostMapping("/email-confirm")
    public void sendAuthNumToEmail(@RequestBody TempAuthInfo tempAuthInfo){
        // 메일로 인증번호 발송
        System.out.println("email === "+ tempAuthInfo.getUsername());
        userService.sendAuthNumToEmail(tempAuthInfo.getUsername());
    }

    // 비밀번호 찾기 화면
    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "forgot-password";
    }


    // 4. 이메일로 임시 비밀번호 전송
    @ResponseBody
    @PostMapping("/forgot-password")
    public void forgotId(@RequestBody TempAuthInfo tempAuthInfo){
        // 메일로 임시 비밀번호 발송
        System.out.println("컨트롤러 --- tempAuthInfo.getUsername() = " + tempAuthInfo.getUsername());
        userService.sendTempPwToEmail(tempAuthInfo.getUsername());
    }

    // 비밀번호 변경 화면
    @GetMapping("/change-password")
    public String changePasswordForm() {
        return "change-password";
    }


    // 비밀번호 변경
    @ResponseBody
    @PostMapping("/change-password")
    public void changePassword(@RequestBody PasswordDto passwordDto) throws UserException {
        String test = user.getUsername();
        System.out.println("test!! = " + test);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal1 = authentication.getPrincipal();
        System.out.println("principal1 = " + principal1);
        System.out.println("authentication.getName() = " + authentication.getName());
//        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
//       String userName = principal.getUsername();
//        System.out.println("userName = " + userName);
        System.out.println("user.getUsername() = " + user.getUsername());
        passwordDto.setUsername(user.getUsername());

        // 왜 static 유저에 저장이 안될까ㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠ.........

//        passwordDto.setUsername("yocu1784@naver.com");
        userService.changePw(passwordDto);

    }


    // 4. 로그인 화면
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }


    // 4. 로그인
    @ResponseBody
    @PostMapping("/loginTest")
    public UserDto login(@RequestBody UserDto userDto) throws UserException {
//        Object a = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("TEST");
        System.out.println("  홈 컨트롤러 / 로그인 -  " + userDto.getUsername());
        System.out.println("  홈 컨트롤러 / 로그인 -  " + userDto.getPassword());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();

        System.out.println(" 홈 컨트롤러 / 로그인 - authentication = " + authentication);
        System.out.println(" 홈 컨트롤러 / 로그인 - authentication.getName() = " + authentication.getName());
        System.out.println(" 홈 컨트롤러 / 로그인 - .getAuthorities() = " + authentication.getAuthorities());
        System.out.println(" 홈 컨트롤러 / 로그인 - authentication.getDetails() = " + authentication.getDetails());
        System.out.println(" 홈 컨트롤러 / 로그인 - authentication.getCredentials() = " + authentication.getCredentials());
        System.out.println(" 홈 컨트롤러 / 로그인 - authentication.getPrincipal() = " + authentication.getPrincipal());
        System.out.println("principal = " + principal.getUsername());

        UserDto userDto1 = principal.getUserDto();
        user = userDto1;
        userService.login(userDto);
        System.out.println("유저: "+ user.getUsername());
        return userDto1;
    }

    // 게시판 화면 (메인 화면)
    @GetMapping("/board")
    public String board() {
        return "board";
    }




}
