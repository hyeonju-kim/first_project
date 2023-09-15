package com.example.first.service;

import com.example.first.dto.PasswordDto;
import com.example.first.dto.TempAuthInfo;
import com.example.first.dto.UserDto;
import com.example.first.exception.UserException;
import com.example.first.mapper.HomeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final ApplicationEventPublisher eventPublisher;
    private final HomeMapper homeMapper;
    private final BCryptPasswordEncoder encoder;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationManager authenticationManager;


    private static final String NUMBER = "0123456789";

    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private static final String SPECIAL_CHAR = "!@#$%^&*()-_=+\\|[]{};:'\",.<>?/";
    private static final String PASSWORD_ALLOW_BASE = CHAR_LOWER + CHAR_UPPER + NUMBER + SPECIAL_CHAR;

    private static final Random RANDOM = new Random();


    // jwt 토큰으로 현재 인증된 사용자의 Authentication 객체에서 이름 가져오기
    public String getUsernameFromAuthentication() {
        String username = null;
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            // 인증된 사용자의 이름 가져오기
            username = authentication.getName();
        }
        return username;
    }


    // 회원가입
    @Override
    public UserDto signUp(UserDto userDto) throws UserException {
        UserDto foundUserByUsername = homeMapper.findByUsername(userDto.getUsername());

        // 이미 가입된 계정일 때
//        System.out.println("디비에서 가져온 유저 dto의 유저네임 ::: " + foundUserByUsername.getUsername());
        if (foundUserByUsername != null) {
            throw new UserException("해당 이메일이 이미 존재합니다.", HttpStatus.BAD_REQUEST, null);
        }

//        // 비밀번호 확인이 비밀번호와 다를 때
//        if (!userDto.getPassword().equals(userDto.getPasswordConfirm())) {
//            throw new UserException("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST, null);
//        }

        String encodedPassword = encoder.encode(userDto.getPassword());
        userDto.setPassword(encodedPassword);

        System.out.printf("인코딩된 비밀번호 : " + encodedPassword);

        return homeMapper.signUp(userDto);
    }

    // 회원가입 시 6자리 인증번호 생성 메서드
    public static String generatorAuthNumber() {
        StringBuilder password = new StringBuilder();

        for(int i = 0; i < 6; i++) { // 항상 6자리 숫자를 생성
            int randomIndex = RANDOM.nextInt(NUMBER.length());
            char randomChar = NUMBER.charAt(randomIndex);
            password.append(randomChar);
        }
        return password.toString();
    }




    // 회원가입 시 메일로 인증번호 발송
    @Override
    public void sendAuthNumToEmail(String username) {
        // 인증번호 생성
        String authNumber = generatorAuthNumber();

        // 임시 유저 정보 생성
        TempAuthInfo tempAuthInfo = new TempAuthInfo();
        tempAuthInfo.setUsername(username);
        tempAuthInfo.setAuthNumber(authNumber);

        // 디비에 인증번호 저장
        homeMapper.setAuth(tempAuthInfo);

        // 메일 전송 이벤트 퍼블리싱(비동기)
        eventPublisher.publishEvent(tempAuthInfo);
    }


    // 임시 비밀번호 생성 메서드
    public static String instancePasswordGenerator() {
        int passwordLength = RANDOM.nextInt(9) + 8; // 8에서 16 사이의 랜덤 길이

        StringBuilder password = new StringBuilder();

        for (int i = 0; i < passwordLength; i++) {
            int randomIndex = RANDOM.nextInt(PASSWORD_ALLOW_BASE.length());
            char randomChar = PASSWORD_ALLOW_BASE.charAt(randomIndex);
            password.append(randomChar);
        }

        return password.toString();
    }


    // 비밀번호 찾기 - 메일로 임시 비밀번호 전송
    @Override
    public void sendTempPwToEmail(String username) {

        // 임시 비밀번호 생성
        String tempPw = instancePasswordGenerator();
        System.out.println("tempPw = " + tempPw);

        // 임시 유저 정보 생성
        TempAuthInfo tempAuthInfo = new TempAuthInfo();
        tempAuthInfo.setUsername(username);
        tempAuthInfo.setAuthNumber(tempPw);

        //디비에 인증정보 저장
        homeMapper.setAuth(tempAuthInfo);

        // 메일 전송 이벤트 퍼블리싱(비동기)
        eventPublisher.publishEvent(tempAuthInfo);
    }

    // 비밀번호 변경
    @Override
    public void changePw(PasswordDto passwordDto) throws UserException {



        System.out.println("passwordDto.getPassword() === " + passwordDto.getPassword());
        System.out.println("passwordDto.getNewPassword() === " + passwordDto.getNewPassword());

        // 1. 사용자 정보를 가져옴
        UserDto user = homeMapper.findByUsername(passwordDto.getUsername());

        if (user == null) {
            throw new UserException("디비에서 가져온 유저디티오 객체가 널이에요ㅠ", HttpStatus.BAD_REQUEST, null);
        }
        System.out.println("user.getPassword() === " + user.getUsername());

//        // 2. 디비에 저장된 암호화된 비밀번호와 입력한 현재 비밀번호를 비교
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        if (passwordEncoder.matches(passwordDto.getPassword(), user.getPassword())) {
//            // 3. 현재 비밀번호가 일치하는 경우, 새 비밀번호를 암호화하여 업데이트
//            String newPasswordEncoded = passwordEncoder.encode(passwordDto.getNewPassword());
//            passwordDto.setNewPassword(newPasswordEncoded);
//            homeMapper.changePw(passwordDto);
//        } else {
//            // 4. 비밀번호가 일치하지 않는 경우 예외 또는 에러 처리
//            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
//        }

//        if (!passwordDto.getPassword().equals(user.getPassword())) {
//            throw new UserException("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST, null);
//        }
        // 3. 새 비밀번호를 암호화하여 업데이트
        String newPasswordEncoded = encoder.encode(passwordDto.getNewPassword());
        passwordDto.setNewPassword(newPasswordEncoded);
        passwordDto.setUsername(user.getUsername());

        System.out.println("newPasswordEncoded === " + newPasswordEncoded);
        System.out.println("user.getUsername()" + user.getUsername());
        homeMapper.changePw(passwordDto);
    }


    // 로그인
    @Override
    public UserDetails login(UserDto userDto) throws UserException {
        String username = userDto.getUsername();
        String password = userDto.getPassword();
        UserDto retrievedUser = homeMapper.findByUsername(username);

        System.out.println("foundUserDto.getUsername() ==============" + retrievedUser.getUsername());
        System.out.println("foundUserDto ==============" + retrievedUser);

        // 조회한 비밀번호
        String foundPw = retrievedUser.getPassword();

        System.out.println("로그인 시 입력한 비밀번호 ====== " + password);
        System.out.println("디비에 저장되어 있는 비밀번호 ====== " + foundPw);

        //비밀번호 같은지 여부 파악
        if (!encoder.matches(password, foundPw)) {
            throw new UserException("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST, null);
        }

        // 2. 인증 성공(회원저장소에 해당 이름이 있으면) 후 인증된 user의 정보를 갖고옴
        System.out.println("key==========RT:" + username);
        return userDetailsService.loadUserByUsername(userDto.getUsername());


    }

    // 마이페이지 정보 가져오기
//    @Override
//    public UserDto getUserInfo() {
//        String usernameFromAuthentication = getUsernameFromAuthentication();
//        return homeMapper.getUserInfo(usernameFromAuthentication);
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
