package com.example.first.service;

import com.example.first.dto.PasswordDto;
import com.example.first.dto.ProfilePicture;
import com.example.first.dto.TempAuthInfo;
import com.example.first.dto.UserDto;
import com.example.first.exception.UserException;
import com.example.first.mapper.HomeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{ // 🔥 11개 메소드 정의
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

    // 🔥 1. 회원가입
    @Override
    public UserDto signUp(UserDto userDto) throws UserException {
        UserDto foundUserByUsername = homeMapper.findByUsername(userDto.getUsername());

        if (foundUserByUsername != null) {
            throw new UserException("해당 이메일이 이미 존재합니다.", HttpStatus.BAD_REQUEST, null);
        }
        // BMI 계산
        BigDecimal bmi = calculateBMI(userDto.getGender(), userDto.getHeight(), userDto.getWeight());
        userDto.setBmi(bmi);

        // 필요 칼로리 계산
        double requiredCalories = calculateDailyCalories(userDto.getGender(), userDto.getHeight(), userDto.getWeight());
        userDto.setRequiredCalories(requiredCalories);

        // 가입일 지정
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String regDate = now.format(formatter); // 날짜를 문자열로 포맷팅

        // 문자열을 LocalDateTime로 파싱하여 저장
        userDto.setRegDate(regDate);

        String encodedPassword = encoder.encode(userDto.getPassword());
        userDto.setPassword(encodedPassword);

        // 관리자 지정
        if (userDto.getRole() != null && userDto.getRole().equals("admin")) {
            userDto.setRole("admin");
        } else {
            userDto.setRole("user");
        }
        return homeMapper.signUp(userDto);
    }

    // 🔥 2. BMI 계산 공식
    public static BigDecimal calculateBMI(String gender, double height, double weight) {
        BigDecimal  bmi;
        if (Objects.equals(gender, "male")) {
            // 남성의 경우 BMI 계산
            bmi = BigDecimal.valueOf(weight / (height * height) * 10000);
        } else {
            // 여성의 경우 BMI 계산 (여성의 경우 평균적으로 체지방률이 조금 높기 때문에 상수를 조정)
            bmi = BigDecimal.valueOf(weight / (height * height) * 1.1 * 10000);
        }
        // 소수점 첫째 자리까지 나타냄
//        return Math.round(bmi * 10.0) / 10.0;
        return bmi;
    }

    // 🔥 3. 하루 권장 칼로리 계산 (Mifflin-St Jeor Equation 공식을 사용)
    public static double calculateDailyCalories(String gender, double height, double weight) {
        double activityLevelFactor = 1.55; // "보통 활동" 활동 수준 계수 가정
        int age = 30; // 나이를 30세로 가정
        double baseCalories;

        if (Objects.equals(gender, "male")) {
            baseCalories = 10 * weight + 6.25 * height - 5 * age + 5;
        } else {
            baseCalories = 10 * weight + 6.25 * height - 5 * age - 161;
        }

        // "보통 활동" 활동 수준에 따른 칼로리 계산
        double requiredCalories = baseCalories * activityLevelFactor;

        // 소수점 첫째 자리까지 나타냄
        return Math.round(requiredCalories * 10.0) / 10.0;
    }

    // 🔥 4. 프로필 사진 경로 반환 및 업로드
    public String storeProfilePicture(MultipartFile profilePicture, String fileName, String username, String originalName) throws IOException {
        // 프로필 사진을 저장하고 파일 경로를 반환하는 로직
        // 이 부분에서 파일을 실제로 저장하고 경로를 반환하는 코드를 작성합니다.
        String savePath = "C:\\profile_picture\\" + fileName;
        // 현재 시간 지정
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String regDate = now.format(formatter);

        String fileExt = getFileExtension(fileName);

        ProfilePicture picture = new ProfilePicture(fileName, savePath, regDate, profilePicture.getBytes(), fileExt, username, originalName);
        homeMapper.storeProfilePicture(picture);

        return savePath;
    }

    // 🔥 5. 파일 이름에서 확장자를 추출하는 메서드
    public static String getFileExtension(String fileName) {
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            return ""; // 확장자가 없는 경우 빈 문자열 반환
        }
    }

    // 🔥 6. 회원가입 시 6자리 인증번호 생성 메서드
    public static String generatorAuthNumber() {
        StringBuilder password = new StringBuilder();

        for(int i = 0; i < 6; i++) { // 항상 6자리 숫자를 생성
            int randomIndex = RANDOM.nextInt(NUMBER.length());
            char randomChar = NUMBER.charAt(randomIndex);
            password.append(randomChar);
        }
        return password.toString();
    }

    // 🔥 7. 회원가입 시 메일로 인증번호 발송
    @Override
    public void sendAuthNumToEmail(String username) {
        // 인증번호 생성
        String authNumber = generatorAuthNumber();

        // 임시 유저 정보 생성
        TempAuthInfo tempAuthInfo = new TempAuthInfo();
        tempAuthInfo.setUsername(username);
        tempAuthInfo.setAuthNumber(authNumber);
        tempAuthInfo.setCreatedAt(LocalDateTime.now());

        // 디비에 인증번호 저장
        homeMapper.setAuth(tempAuthInfo);

        // 메일 전송 이벤트 퍼블리싱(비동기)
        eventPublisher.publishEvent(tempAuthInfo);
    }

    ////////////////////////////////// 여기까지 회원가입 시 사용하는 메서드 /////////////////////////////////////


    // 🔥 8. 임시 비밀번호 생성 메서드
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


    // 🔥 9. 비밀번호 찾기 - 메일로 임시 비밀번호 전송
    @Override
    public void sendTempPwToEmail(String username) {
        // 임시 비밀번호 생성
        String tempPw = instancePasswordGenerator();

        // 임시 유저 정보 생성
        TempAuthInfo tempAuthInfo = new TempAuthInfo();
        tempAuthInfo.setUsername(username);
        tempAuthInfo.setAuthNumber(tempPw);

        //디비에 인증정보 저장
        homeMapper.setAuth(tempAuthInfo);

        // 메일 전송 이벤트 퍼블리싱(비동기)
        eventPublisher.publishEvent(tempAuthInfo);
    }

    // 🔥 10. 비밀번호 변경
    @Override
    public void changePw(PasswordDto passwordDto) throws UserException {
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


    // 🔥 11. 로그인
    @Override
    public UserDetails login(UserDto userDto) throws UserException {
        String username = userDto.getUsername();
        String password = userDto.getPassword();
        UserDto retrievedUser = homeMapper.findByUsername(username);

        // 현재 로그인한 시간을 lastLoginDate에 업데이트
        LocalDateTime now = LocalDateTime.now();
        userDto.setLastLoginDate(now);
        homeMapper.updateUserLastLoginDate(userDto);

        // 조회한 비밀번호
        String foundPw = retrievedUser.getPassword();

        //비밀번호 같은지 여부 파악
        if (!encoder.matches(password, foundPw)) {
            throw new UserException("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST, null);
        }

        // 2. 인증 성공(회원저장소에 해당 이름이 있으면) 후 인증된 user의 정보를 갖고옴
        System.out.println("key==========RT:" + username);
        return userDetailsService.loadUserByUsername(userDto.getUsername());
    }

    // 🔥 12. 마이페이지 화면 조회

}
