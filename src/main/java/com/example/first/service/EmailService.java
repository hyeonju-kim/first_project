package com.example.first.service;

import com.example.first.dto.TempAuthInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    private final JavaMailSender javaMailSender;

    @EventListener(TempAuthInfo.class)
    @Async("threadPoolTaskExecutor")
    public void sendPasswordForgotMessage(TempAuthInfo tempAuthInfo){
        log.info("send Email");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("hyeonju.kim@weaverloft.com");
        message.setTo(tempAuthInfo.getUsername());
        message.setSubject("회원가입 인증번호 발송 안내");
        message.setText("인증번호 : " + tempAuthInfo.getAuthNumber());
        javaMailSender.send(message);
    }

}
