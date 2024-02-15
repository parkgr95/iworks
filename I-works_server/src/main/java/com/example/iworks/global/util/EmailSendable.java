package com.example.iworks.global.util;

import org.springframework.stereotype.Component;

@Component
public interface EmailSendable {
    void send(String to, String subject, String message) throws InterruptedException;
}