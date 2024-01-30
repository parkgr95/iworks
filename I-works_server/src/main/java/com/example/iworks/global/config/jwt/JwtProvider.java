package com.example.iworks.global.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class JwtProvider {

    @Value("${jwt.accessExpTime}")
    long accessExpTime;

    @Value("${jwt.refreshExpTime}")
    long refreshExpTime;
    private final RedisTemplate<String, String> redisTemplate;
    private final JwtSecretKey key;

    public JwtProvider(@Qualifier("redisTemplate") RedisTemplate<String, String> redisTemplate, JwtSecretKey key) {
        this.redisTemplate = redisTemplate;
        this.key = key;
    }

    public String createAccessToken(String eid, List<String> role) {
        String accessToken = Jwts.builder()
                .setSubject(eid)
                .claim("type","refresh")
                .claim("role",role)
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpTime))
                .signWith(key.secretKey())
                .compact();
        return accessToken;
    }

    public String reCreateAccessToken(String refreshToken) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key.secretKey())
                .build()
                .parseClaimsJws(refreshToken)
                .getBody();

        String eid = claims.getSubject();
        List<String> role = (List<String>)claims.get("role");
        return createAccessToken(eid, role);
    }

    public String createRefreshToken(String eid, List<String> role) {
        String refreshToken = Jwts.builder()
                .setSubject(eid)
                .claim("type","refresh")
                .claim("role",role)
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpTime))
                .signWith(key.secretKey())
                .compact();
        redisTemplate.opsForValue().set(
                refreshToken, //key
                eid, //value
                refreshExpTime,
                TimeUnit.MILLISECONDS
        );

        return refreshToken;
    }

    public Boolean validateAccessToken(String accessToken) {
        System.out.println("val access");
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key.secretKey())
                .build()
                .parseClaimsJws(accessToken)
                .getBody();
        String type = (String)claims.get("type");
        String eid = claims.getSubject();
        return "access".equals(type) && eid != null;
    }

    public Boolean validateRefreshToken(String refreshToken) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key.secretKey())
                .build()
                .parseClaimsJws(refreshToken)
                .getBody();
        String type = (String)claims.get("type");
        if (type.equals("refresh")) {
            System.out.println("success");
            ValueOperations<String, String> stringValueOperations = redisTemplate.opsForValue();
            String redisValue = stringValueOperations.get(refreshToken);
            if (redisValue != null) {
                return claims.getExpiration().before(new Date());
            }
        }
        System.out.println("failed");
        return false;
    }

    public String getUserEid(String jwt) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key.secretKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();
        return claims.getSubject();
    }

    public List<String> getUserRole(String jwt) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key.secretKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();
        return (List<String>)claims.get("role");
    }

}
