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
//            "/configuration/ui",    // 스프링 Fox Swagger UI 설정
//            "/configuration/security",    // 스프링 Fox Swagger UI 설정
//            "/webjars/**",    // 스프링 Fox Swagger UI 설정
//            "/h2/**",    // H2 데이터베이스 콘솔 접근 허용
//            "/h2-console/**",    // H2 데이터베이스 콘솔 접근 허용
            "/css/**",    // CSS 리소스
            "/js/**",    // JavaScript 리소스
            "/scss/**",    // SCSS 리소스
            "/vendor/**",    // Vendor 리소스
            "/img/**",    // 이미지 리소스
            // "/boards/**", // 주석처리된 경로 (특정 경로에 대한 보안 설정을 해제하려면 주석 해제)
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

    //이 메서드는 Spring Security가 정적 리소스와 같은 특정 웹 리소스에 대한 보안을 무시하도록 구성하는 데 사용됩니다.
    //web.ignoring().antMatchers(AUTH_WHITE_LIST)는 AUTH_WHITE_LIST 배열에 포함된 경로 패턴들을 무시하도록 설정합니다.
    // 이 경로 패턴에 매칭되는 요청은 Spring Security의 인증 및 권한 검사를 거치지 않고 접근이 허용됩니다.
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(AUTH_WHITE_LIST);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception { //이 메서드는 HTTP 보안 설정을 구성하는 데 사용됩니다.
        http.
                csrf().disable() // CSRF(Cross-Site Request Forgery) 공격 방지를 비활성화합니다. CSRF 보호를 비활성화하면 CSRF 토큰 검사가 비활성화되며, 모든 HTTP 요청에서 CSRF 토큰을 사용할 필요가 없게 됩니다.
                .headers() // 에디터 적용 위해 추가
                .frameOptions().sameOrigin()// 에디터 적용 위해 추가 . 다른 출처의 프레임에서 해당 페이지를 로드하지 못하도록 X-Frame-Options 헤더를 설정합니다. 이것은 보안을 위한 조치로서, 클릭재킹(CSRF 공격의 한 종류)을 방지하는 데 도움이 됩니다.

                .and()
                .authorizeRequests()// 경로별로 권한 부여 및 인증 구성을 지정하는 부분입니다.
//                .antMatchers("/admin/**").hasRole("ADMIN") // ADMIN 권한을 가진 사용자만 /admin 경로 접근 허용
                .antMatchers("/**").permitAll() // 모든 경로에 대한 인증 없이 접근을 허용하는 설정입니다. 이 부분은 모든 사용자가 어떤 경로든지 접근할 수 있게 합니다.
                .anyRequest().authenticated() // 그 외의 모든 요청은 인증 필요

                .and()
                .exceptionHandling() // 예외 처리 관련 설정을 구성합니다.

                //사용자가 인증되지 않은 상태로 접근하려고 할 때 호출되는 커스텀 AuthenticationEntryPoint를 설정합니다.
                // 이 커스텀 핸들러는 사용자에게 적절한 오류 메시지 또는 리디렉션을 제공하는 데 사용될 수 있습니다.
                .authenticationEntryPoint(authenticationEntryPoint);
    }
}
