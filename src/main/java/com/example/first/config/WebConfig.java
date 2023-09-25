package com.example.first.config;

import com.example.first.interceptor.SessionCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{

    //file path의 정적리소스 사용을 가능하게 해줍니다.
     @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //solution localhost:8080/img/~ 이렇게 들어오면 아래에서 등록한 filePath에서 정적 리소스를 찾아 리턴해줍니다.
        ResourceHandlerRegistration reg1 = registry.addResourceHandler("img/**");
        //solution 이미지가 있는 파일 경로를 등록해줍니다.
        reg1.addResourceLocations("file:C:\\profile_picture\\");


        // 새로운 리소스 핸들러 등록
         ResourceHandlerRegistration reg2 = registry.addResourceHandler("file/**"); // 원하는 URL 패턴을 지정
         reg2.addResourceLocations("file:C:\\multifile\\");
    
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SessionCheckInterceptor())  // 30분 마다 세션 체크하는 인터셉터 추가
                .addPathPatterns("/boards/create")
                .addPathPatterns("/boards/**/addComment")
                .addPathPatterns("/admin/**")
                .addPathPatterns("/mypage/**");

    }
}

