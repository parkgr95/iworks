package com.example.iworks.global.config;

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Configuration
public class SecretKeyConfig {
    @Value("${jwt.secret}")
    private String SECURTY_SECRET_KEY;

    @Value("${openvidu.secret}")
    private String OPEN_VIDU_SECRET_KEY;

    @Bean
    public SecretKey getJwtsecretKey(){
        return new SecretKeySpec(SECURTY_SECRET_KEY.getBytes(), SignatureAlgorithm.HS512.getJcaName());
    }

    @Bean
    public String getOpenViduSecretKey(){
        return OPEN_VIDU_SECRET_KEY;
    }
}
