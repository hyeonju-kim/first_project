package com.example.first.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Map;

@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
                                                                                        throws IOException, ServletException {
        if(request.getMethod().equals(HttpMethod.POST.toString()) || request.getMethod().equals(HttpMethod.PUT.toString())
                                                                    || request.getMethod().equals(HttpMethod.PATCH.toString())) {
            log.error(authException.getMessage());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            OutputStream outputStream = response.getOutputStream();
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(outputStream, errorMessageBox());
            outputStream.flush();
        }else{
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "비정상적인 접근입니다.");
        }
    }

    private static Map<String,String> errorMessageBox(){
        return Collections.singletonMap("message", "비밀번호가 틀립니다.");
    }

    /* 이 Java 클래스는 Spring Security에서 사용되는 AuthenticationEntryPoint 인터페이스를 구현한 클래스로,
    * 인증되지 않은 사용자가 보호된 리소스에 액세스하려고 할 때 실행됩니다. 이 클래스는 다음과 같은 주요 기능을 수행합니다:

        commence 메서드: 인증되지 않은 요청이 들어올 때 호출됩니다. 해당 메서드는 클라이언트 요청에 따라 다음 작업을 수행합니다.
        HTTP 요청 메서드가 POST, PUT, PATCH 중 하나인 경우:
        로깅: 에러 메시지를 로그에 기록합니다.
        HTTP 응답 설정: 상태 코드를 401 (Unauthorized)로 설정하고, 컨텐츠 타입을 JSON으로 설정합니다.
        JSON 응답 메시지: errorMessageBox 메서드를 호출하여 JSON 형식의 오류 메시지를 생성하고 응답으로 전송합니다.
        HTTP 요청 메서드가 POST, PUT, PATCH가 아닌 경우:
        HTTP 응답 설정: 상태 코드를 401 (Unauthorized)로 설정하고, 오류 메시지를 전송합니다.
        errorMessageBox 메서드: JSON 형식의 오류 메시지를 생성합니다. 이 메시지는 클라이언트에게 전달되어 오류 상황을 설명하는 데 사용됩니다.

    이 클래스는 Spring Security 구성에서 사용자가 인증되지 않은 상태로 POST, PUT, PATCH 요청을 보낼 때
    해당 요청을 처리하고 클라이언트에게 적절한 오류 응답을 반환하는 데 사용됩니다.*/
}