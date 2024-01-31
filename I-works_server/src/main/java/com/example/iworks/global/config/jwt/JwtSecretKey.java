package com.example.iworks.global.config.jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Configuration
public class JwtSecretKey {
    @Value("${jwt.secret}")
    String SECRET_KEY;

    @Bean
    public SecretKey secretKey(){
        return new SecretKeySpec(SECRET_KEY.getBytes(), SignatureAlgorithm.HS512.getJcaName());
    }
}
