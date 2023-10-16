//package com.example.first.aop;
//
//import com.example.first.dto.ErrorDto;
//import com.example.first.dto.UserDto;
//import com.example.first.mapper.HomeMapper;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.AfterThrowing;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDateTime;
//
//@Aspect
//@Component
//@Slf4j
//@RequiredArgsConstructor
//public class LogAopComponent {
//    private final HomeMapper homeMapper;
////    private Logger logger = LoggerFactory.getLogger(LogAopComponent.class);
//
//    // HomeController의 모든 메서드가 실행될 때, 아래 "test" 로그 찍기
//    @AfterReturning(pointcut = "execution(* com.example.first.controller.HomeController.*(..))", returning = "result")
//    public void logMethodExecution(Object result) {
//        log.info("test");
//    }
//
//    // fisrt 프로젝트 전체에서 에러가 발생할 때, 에러 메시지가 로그로 찍힘
//    @AfterThrowing(pointcut = "execution(* com.example.first..*(..))", throwing = "ex")
//    public void logError(Exception ex) {
//        log.error(String.valueOf(ex));
//        System.out.println("ex = " + ex);
//        System.out.println("ex.getLocalizedMessage() = " + ex.getLocalizedMessage());
//
//
//        String username = "";
//        String nickname = "";
//
//        // ============== 현재 로그인한 사용자 정보 가져오기 ===============
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.isAuthenticated()) {
//            username = authentication.getName(); // 사용자 이메일
//
//            UserDto userDto = homeMapper.findByUsername(username);
//            if (userDto != null) {
//                String role = userDto.getRole();
//                nickname = userDto.getNickname();
//                System.out.println("role ===== " + role);
//                System.out.println("nickname = " + nickname);
//            }
//        } else {
//            // 로그인하지 않은 경우, username을 비워두거나 다른 값을 넣어서 전달
//        }
//        // ==============================================================
//
//        LocalDateTime errTime = LocalDateTime.now();
//        String errorTime = errTime.toString();
//        System.out.println("errorTime ============================ " + errorTime);
//        String errorType = extractExceptionType(String.valueOf(ex));
//        String errorMessage = ex.getLocalizedMessage();
//
//        // TODO  -> username, nickname 사용자 걸로 수정 ( )
//        ErrorDto errorDto = new ErrorDto("username_test", "nickname_test", errorTime, errorType, errorMessage);
//        homeMapper.insertError(errorDto);
//        System.out.println("에러 정보 인서트 완료!!");
//    }
//
//
//    public static String extractExceptionType(String ex) {
//        // 문자열에서 ':' 문자를 기준으로 분할
//        String[] parts = ex.split(":");
//
//        if (parts.length > 0) {
//            // 맨 앞의 부분을 가져와서 공백을 제거한 후 반환
//            String errorType = parts[0].trim();
//            return errorType;
//        } else {
//            return ""; // 적절한 예외 유형을 찾지 못한 경우 빈 문자열 반환
//        }
//    }
//}
