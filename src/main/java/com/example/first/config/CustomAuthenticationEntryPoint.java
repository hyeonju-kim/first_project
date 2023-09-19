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
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        if(request.getMethod().equals(HttpMethod.POST.toString()) || request.getMethod().equals(HttpMethod.PUT.toString()) || request.getMethod().equals(HttpMethod.PATCH.toString())) {
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
}