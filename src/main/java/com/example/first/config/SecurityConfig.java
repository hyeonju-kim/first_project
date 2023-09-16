package com.example.first.config;

import com.example.first.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class  SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsServiceImpl userDetailsService;



    /* 스프링 시큐리티 룰을 무시할 URL 규칙 설정
     * 정적 자원에 대해서는 Security 설정을 적용하지 않음 */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/register",  "/css/**").permitAll();
//                .antMatchers("/member/**").authenticated() // 일반사용자 접근 가능
//                .antMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN") // 매니저, 관리자 접근 가능
//                .antMatchers("/admin/**").hasRole("ADMIN"); // 관리자만 접근 가능
        // 인증 필요시 로그인 페이지와 로그인 성공시 리다이랙팅 경로 지정


        // 유진님 코드 //
        http
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .failureUrl("/login?error=true")
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                        response.sendRedirect("/login?error=true");
                    }
                })
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                .userDetailsService(userDetailsService)
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .and()
                .csrf().disable();


//        http.formLogin().loginPage("/login").defaultSuccessUrl("/", true);
//        // 로그인이 수행될 uri 매핑 (post 요청이 기본)
//        http.formLogin().loginProcessingUrl("/login").defaultSuccessUrl("/", true);
//        // 인증된 사용자이지만 인가되지 않은 경로에 접근시 리다이랙팅 시킬 uri 지정
//        http.exceptionHandling().accessDeniedPage("/forbidden");
//        // logout
//        http.logout().logoutUrl("/logout").logoutSuccessUrl("/");
//
//        //1. 내가 만든 서비스 지정
//        http.userDetailsService(userDetailsService);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }



    // 인코더를 여기에서 빈으로 등록
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // Spring Security에 사용자 인증 정보를 제공하고 비밀번호 인코딩에 사용된다.
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
//    }
}
