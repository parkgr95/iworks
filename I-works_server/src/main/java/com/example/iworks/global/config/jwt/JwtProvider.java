package com.example.iworks.global.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
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
    @Value("${jwt.secret}")
    String SECRET_KEY;

    private final RedisTemplate<String, String> redisTemplate;

    @Value("${jwt.accessExpTime}")
    long accessExpTime;

    @Value("${jwt.refreshExpTime}")
    long refreshExpTime;

    public JwtProvider(@Qualifier("redisTemplate") RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String createAccessToken(String eid, List<String> role) {
        String accessToken = JWT.create()
                .withExpiresAt(new Date(System.currentTimeMillis() + accessExpTime)) // 토큰의 만료시간 10분
                .withClaim("type", "access")
                .withClaim("eid", eid)
                .withClaim("role", role)
                .sign(Algorithm.HMAC512(SECRET_KEY));
        return accessToken;
    }

    public String reCreateAccessToken(String refreshToken) {
        String eid = JWT.require(Algorithm.HMAC512(SECRET_KEY)).build().verify(refreshToken).getClaim("eid").asString();
        List<String> role = JWT.require(Algorithm.HMAC512(SECRET_KEY)).build().verify(refreshToken).getClaim("role").asList(String.class);
        return createAccessToken(eid, role);
    }

    public String createRefreshToken(String eid, List<String> role) {
        String refreshToken = JWT.create()
                .withClaim("type", "refresh")
                .withClaim("eid", eid)
                .withClaim("role", role)
                .withExpiresAt(new Date(System.currentTimeMillis() + refreshExpTime)) // 86400000 하루
                .sign(Algorithm.HMAC512(SECRET_KEY));

        redisTemplate.opsForValue().set(
                refreshToken, //key
                eid, //value
                refreshExpTime,
                TimeUnit.MILLISECONDS
        );

        return refreshToken;
    }

    public Boolean validateAccessToken(String accessToken) {
        try{

        System.out.println("val access");
        String type = JWT.require(Algorithm.HMAC512(SECRET_KEY)).build().verify(accessToken).getClaim("type").asString();
        String eid = JWT.require(Algorithm.HMAC512(SECRET_KEY)).build().verify(accessToken).getClaim("eid").asString();
        return "access".equals(type) && eid != null;
        }catch (ExpiredJwtException e){
            throw new ExpiredJwtException(null,null,e.getMessage());
        }
        catch (JwtException e){
            throw new JwtException(e.getMessage());
        }
    }

    public Boolean validateRefreshToken(String refreshToken) {
        String type = JWT.require(Algorithm.HMAC512(SECRET_KEY)).build().verify(refreshToken).getClaim("type").asString();
        if (type.equals("refresh")) {
            System.out.println("success");
            ValueOperations<String, String> stringValueOperations = redisTemplate.opsForValue();
            String redisValue = stringValueOperations.get(refreshToken);
            if (redisValue != null) {
                return JWT.require(Algorithm.HMAC512(SECRET_KEY)).build().verify(refreshToken).getExpiresAt().after(new Date());
            }
        }
        System.out.println("failed");
        return false;
    }

    public String getUserEid(String jwt) {
        return JWT.require(Algorithm.HMAC512(SECRET_KEY)).build().verify(jwt).getClaim("eid").asString();
    }

    public List<String> getUserRole(String jwt) {
        return JWT.require(Algorithm.HMAC512(SECRET_KEY)).build().verify(jwt).getClaim("role").asList(String.class);
    }
}
