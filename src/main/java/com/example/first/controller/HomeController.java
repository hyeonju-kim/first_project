package com.example.first.controller;

import com.example.first.dto.PasswordDto;
import com.example.first.dto.TempAuthInfo;
import com.example.first.dto.UserDto;
import com.example.first.exception.UserException;
import com.example.first.mapper.HomeMapper;
import com.example.first.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class HomeController {  // 🎯🎯🎯🎯🎯 12개 API
    private final UserService userService;
    private final HomeMapper homeMapper;
    private final AuthenticationManager authenticationManager;
    public String getUsername() {
        String username = null;
        UserDto userDto = null;
        // ============== 현재 로그인한 사용자 정보 가져오기 ===============
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            username = authentication.getName(); // 사용자 이메일

            userDto = homeMapper.findByUsername(username);
            if (userDto != null) {
                String role = userDto.getRole();
                System.out.println("role ===== " + role);
            }
        } else {
            // 로그인하지 않은 경우, username을 비워두거나 다른 값을 넣어서 전달
        }
        // ==============================================================
        return username;
    }
    public UserDto getUserDto() {
        String username = null;
        UserDto userDto = null;
        // ============== 현재 로그인한 사용자 정보 가져오기 ===============
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            username = authentication.getName(); // 사용자 이메일

            userDto = homeMapper.findByUsername(username);
            if (userDto != null) {
                String role = userDto.getRole();
                System.out.println("role ===== " + role);
            }
        } else {
            // 로그인하지 않은 경우, username을 비워두거나 다른 값을 넣어서 전달
        }
        // ==============================================================
        return userDto;
    }

    // 🎯 1. 회원가입 폼
    @GetMapping("/register")
    public String signupForm() {
        return "register";
    }

    //🎯  2. 회원가입
    @PostMapping("/register")
    @ResponseBody
    public UserDto signup(@RequestBody UserDto userDto) throws UserException, IOException {
        try {
            return userService.signUp(userDto);
        } catch (UserException e) {
            throw new RuntimeException("서버에서 에러가 발생했습니다.");
        }
    }

    // 🎯 3. 프로필 사진 업로드
    @PostMapping("/upload-profilePicture")
    public ResponseEntity<String> uploadProfilePicture(@RequestParam("uploadFile") MultipartFile profilePicture,
                                                       @RequestParam("username") String username) throws IOException {
        // 내가 업로드 파일을 저장할 경로
        String originalName = profilePicture.getOriginalFilename();
        String fileName = System.currentTimeMillis() + "_" + originalName;

        // 업로드 할 디렉토리 경로 설정
        String savePath = "C:\\profile_picture";
        // 저장할 파일, 생성자로 경로와 이름을 지정해줌.
        File saveFile = new File(savePath, fileName);

        userService.storeProfilePicture(profilePicture, fileName, username, originalName);
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

    // 🎯 4. 이메일로 인증번호 전송
    @ResponseBody
    @PostMapping("/email-confirm")
    public void sendAuthNumToEmail(@RequestBody TempAuthInfo tempAuthInfo) {
        // 메일로 인증번호 발송
        userService.sendAuthNumToEmail(tempAuthInfo);
    }

    //////////////////// 1~4 api 가 회원가입 !!

    // 🎯 5. 비밀번호 찾기 화면
    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "forgot-password";
    }

    // 🎯 6. 이메일로 임시 비밀번호 전송
    @ResponseBody
    @PostMapping("/forgot-password")
    public void forgotId(@RequestBody TempAuthInfo tempAuthInfo) {
        userService.sendTempPwToEmail(tempAuthInfo.getUsername());
    }

    // 🎯 7. 비밀번호 변경 폼
    @GetMapping("/change-password")
    public String changePasswordForm() {
        return "change-password";
    }

    // 🎯 8. 비밀번호 변경
    @ResponseBody
    @PostMapping("/change-password")
    public void changePassword(@RequestBody PasswordDto passwordDto) throws UserException {
        String username = getUsername();
        passwordDto.setUsername(username);
        userService.changePw(passwordDto);
    }


    // 🎯 9. 로그인 화면
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    // 🎯 10. 로그인
    @ResponseBody
    @PostMapping("/login")
    public UserDto login(@RequestBody UserDto userDto) throws UserException {

        // 아래 코드는 없어도 되지만, 명시적으로 userDto를 사용해서 인증객체를 만들어 주기 위해서 지정함
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        userService.login(userDto);
        return userDto;
    }

    // 🎯 11. 로그아웃
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        // SecurityContextLogoutHandler를 사용하여 현재 사용자를 로그아웃합니다.
        // 이 클래스는 Spring Security에서 제공하는 로그아웃 처리 도우미입니다.
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, response, SecurityContextHolder.getContext().getAuthentication());

        // 로그아웃 후 리다이렉트할 페이지를 지정 (예: 홈 페이지)
        return "redirect:/boards";
    }

    // 🎯 12. 마이페이지 화면 조회
    @GetMapping("/mypage")
    public ModelAndView mypage(Model model) {
        String username = getUsername();
        UserDto userDto = getUserDto();

        // 프로필 사진 경로 가져와서 저장하기 (미리 저장 못해서 일단 이렇게 가져와서 넣어주자,,)
        String originalName = homeMapper.findProfilePictureOriginalName(username);
        String profilePictureSavePath = homeMapper.findProfilePictureSavePath(username);
        String profilePictureFileName = homeMapper.findProfilePictureFileName(username);

        userDto.setOriginalName(originalName);
        userDto.setProfilePictureLocation(profilePictureSavePath);

        Map<String, Object> params = new HashMap<>();
        params.put("savePath", profilePictureSavePath);
        params.put("userDto", userDto);

        homeMapper.updateUserInsertSavePath(params);

        //solution /img/ 아래 쓰고싶은 파일 이름만 적어줍니다.
        File file = new File("/img/"+profilePictureFileName);

        ModelAndView mv = new ModelAndView();
        mv.setViewName("mypage");
        mv.addObject("file", file);
        mv.addObject("user", userDto);

        model.addAttribute("username", username);
        model.addAttribute("nickname", userDto.getNickname());
        model.addAttribute("role", userDto.getRole());
        return mv;
    }
}
