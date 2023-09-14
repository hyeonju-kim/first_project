package com.example.first.config;


import com.example.first.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtil jwtUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. 헤더 파싱
        String authorization = request.getHeader("Authorization");
        String username = "";
        String token = "";

        // 2. 토큰 파싱 해서 name 얻어오기
        if (authorization != null && authorization.startsWith("Bearer ")) {
            token = authorization.substring(7);
            username = jwtUtil.getUsernameFromToken(token);

            log.info("username = {}", username);

        }else{
            filterChain.doFilter(request, response);
            return;
        }

        // 3. 토큰은 있는데 현재 인증객체가 없으면 컨텍스트홀더에 유저토큰 세팅
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            log.info("userDatails = {}", userDetails);

            // 토큰 유효여부 확인
//            if (jwtUtil.isValidToken(token, userDetails)) {
//
                // 230814 추가 - Redis 에 해당 Access Token logout여부 확인
//                String isLogout = (String)redisTemplate.opsForValue().get(token);

                // logout딱지가 없다면 토큰 만들어주고, logout딱지가 있으면 토큰 안만들어줌~!!
//                if (ObjectUtils.isEmpty(isLogout)) {
//                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//                }
//            }

            // 액세스 토큰이 만료되었을 경우 리프레시 토큰을 사용하여 새로운 액세스 토큰 발급
            if (jwtUtil.isTokenExpired(token)) {
//                // Redis에서 해당 리프레시 토큰 가져오기
//                String refreshToken = (String) redisTemplate.opsForValue().get("RT:" + username);

                if (jwtUtil.isValidToken(token, userDetails)) {
                    // 리프레시 토큰이 유효하면 새로운 액세스 토큰 생성
                    String newAccessToken = jwtUtil.generateAccessToken(userDetails);

                    // 새로운 액세스 토큰을 Response 헤더에 설정
                    response.setHeader("Authorization", "Bearer " + newAccessToken);

                    // SecurityContextHolder에 새로운 Authentication 설정
                    UsernamePasswordAuthenticationToken newAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    newAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(newAuthenticationToken);
                    System.out.println("jwt필터에서 인증객체에 잘 저장되는지 확인 === "+SecurityContextHolder.getContext().getAuthentication());

//                    // 이전 액세스 토큰을 Redis에 저장하여 로그아웃 처리 (Blacklist 역할)
//                    redisTemplate.opsForValue().set(token, "logout", jwtUtil.getExpirationDate(token).getTime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
                }
            }

        }
        filterChain.doFilter(request, response);

    }


}