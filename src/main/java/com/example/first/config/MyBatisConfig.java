package com.example.first.config;

import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
public class MyBatisConfig {

    @Bean
    public ConfigurationCustomizer mybatisConfigurationCustomizer() {
        return configuration -> {
            // 커스텀 Type Handler를 등록합니다.
            configuration.getTypeHandlerRegistry().register(MultipartFile.class, MultipartFileTypeHandler.class);
        };
    }

    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("UTF-8"); // 파일 인코딩 설정
        resolver.setMaxUploadSize(5 * 1024 * 1024); // 업로드 파일 크기 제한 설정 (예: 5MB)
        return resolver;
    }
}