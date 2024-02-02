package com.example.iworks.global.util;

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Configuration
public class SecretKeyUtil {
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
        return "Basic "+Base64.getEncoder().encodeToString(("OPENVIDUAPP:"+OPEN_VIDU_SECRET_KEY).getBytes());
    }
}
