package com.example.iworks.global.util;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class SimpleEmailSendable implements EmailSendable {
    private final JavaMailSender javaMailSender;

    public SimpleEmailSendable(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender; //MailSender 인터페이스를 상속받으며, MIME을 지원한다
    }

    @Override
    public void send(String to, String subject, String message) {
        // 보낸 사람, 받는 사람, 참조, 제목 및 텍스트를 포함하는 메시지를 만든다
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to); //받는 사람 주소
        mailMessage.setSubject(subject); //제목
        mailMessage.setText(message); //메시지 내용
        javaMailSender.send(mailMessage); //메일 발송
    }

}