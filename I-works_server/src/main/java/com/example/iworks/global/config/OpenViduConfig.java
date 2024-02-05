package com.example.iworks.global.config;

import io.openvidu.java.client.OpenVidu;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenViduConfig {
    private final OpenVidu openVidu;

    public OpenViduConfig(@Value("${openvidu.host}")String OPENVIDU_SERVER_URL, SecretKeyConfig secretKeyConfig) {
        String SECRET_KEY = secretKeyConfig.getOpenViduSecretKey();
        this.openVidu = new OpenVidu(OPENVIDU_SERVER_URL,secretKeyConfig.getOpenViduSecretKey());
    }

    @Bean
    public OpenVidu getOpenVidu(){
        return this.openVidu;
    }

}
