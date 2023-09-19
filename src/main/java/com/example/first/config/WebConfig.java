package com.example.first.config;

import org.springframework.context.annotation.Configuration;
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
        reg1.addResourceLocations("file:C:\\Program Files\\hj\\first_project\\profile_picture\\");



    
    }
}
