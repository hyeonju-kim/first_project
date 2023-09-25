package com.example.first.interceptor;


import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession(false); // 세션이 없으면 null 반환

        System.out.println("session = " + session);

        if (session != null) {
            // 세션이 존재하고 사용자 정보가 있는 경우, 세션 만료 시간을 설정
            int sessionTimeoutInSeconds = 1800; // 30분
//            int sessionTimeoutInSeconds = 10; // 10초

            session.setMaxInactiveInterval(sessionTimeoutInSeconds);

            int maxInactiveInterval = session.getMaxInactiveInterval();
            System.out.println("maxInactiveInterval = " + maxInactiveInterval);

            return true; // 세션 체크가 통과되면 true 반환

        } else {

            response.sendRedirect("/login"); // alert.jsp로 리다이렉트
            return false; // 세션 체크를 통과하지 못하면 false 반환
        }
    }
}