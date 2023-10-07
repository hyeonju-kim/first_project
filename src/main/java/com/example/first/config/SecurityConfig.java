package com.example.first.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class  SecurityConfig extends WebSecurityConfigurerAdapter {
    private final AuthenticationEntryPoint authenticationEntryPoint;

    // 인증을 무시할 URL 경로를 설정
    private static final String[] AUTH_WHITE_LIST = {
            "/configuration/ui",    // 스프링 Fox Swagger UI 설정
            "/configuration/security",    // 스프링 Fox Swagger UI 설정
            "/webjars/**",    // 스프링 Fox Swagger UI 설정
            "/h2/**",    // H2 데이터베이스 콘솔 접근 허용
            "/h2-console/**",    // H2 데이터베이스 콘솔 접근 허용
            "/css/**",    // CSS 리소스
            "/js/**",    // JavaScript 리소스
            "/scss/**",    // SCSS 리소스
            "/vendor/**",    // Vendor 리소스
            "/img/**",    // 이미지 리소스
            // "/boards/**", // 주석처리된 경로 (특정 경로에 대한 보안 설정을 해제하려면 주석 해제)
            "/store-img/**"    // 상점 이미지 리소스
    };

    @Bean
    public BCryptPasswordEncoder encodePassword(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(AUTH_WHITE_LIST);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                csrf().disable()
                .headers() // 에디터 적용 위해 추가
                .frameOptions().sameOrigin()// 에디터 적용 위해 추가

                .and()
                .authorizeRequests()
                .antMatchers("/**").permitAll() // 모든 경로에 대한 인증 없이 접근 허용
                .anyRequest().authenticated() // 그 외의 모든 요청은 인증 필요

                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint);
    }
}
