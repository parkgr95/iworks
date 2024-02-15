package com.example.iworks.global.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.List;

@Component
public class JwtProvider {

    @Value("${jwt.accessExpTime}")
    long accessExpTime;

    @Value("${jwt.refreshExpTime}")
    long refreshExpTime;

    private final RedisTemplate<String, String> redisTemplate;

    private final SecretKey SECRET_KEY;

    public JwtProvider(@Qualifier("redisTemplate") RedisTemplate<String, String> redisTemplate, @Value("${jwt.secret}")String key) {
        this.redisTemplate = redisTemplate;
        this.SECRET_KEY = new SecretKeySpec(key.getBytes(), SignatureAlgorithm.HS512.getJcaName());
    }

    public String createAccessToken(int id, List<String> role) {
        String accessToken = Jwts.builder()
                .claim("id",id)
                .claim("type","access")
                .claim("role",role)
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpTime))
                .signWith(SECRET_KEY)
                .compact();
        return accessToken;
    }

    public String reCreateAccessToken(String refreshToken) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(refreshToken)
                .getBody();

        int id = (int) claims.get("id");
        List<String> role = (List<String>)claims.get("role");
        return createAccessToken(id, role);
    }

    public String createRefreshToken(int id, List<String> role) {
        String refreshToken = Jwts.builder()
                .claim("id",id)
                .claim("type","refresh")
                .claim("role",role)
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpTime))
                .signWith(SECRET_KEY)
                .compact();
/*        redisTemplate.opsForValue().set(
                refreshToken, //key
                id, //value
                refreshExpTime,
                TimeUnit.MILLISECONDS
        );*/

        return refreshToken;
    }

    public Boolean validateAccessToken(String accessToken) {
        System.out.println("access check");
        accessToken = accessToken.replace("Bearer ","");
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(accessToken)
                .getBody();
        String type = (String)claims.get("type");
        return "access".equals(type);
    }

    public Boolean validateRefreshToken(String refreshToken) {
        System.out.println("refresh check");
        refreshToken = refreshToken.replace("Bearer ","");
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(refreshToken)
                .getBody();
        String type = (String)claims.get("type");
        if (type.equals("refresh")) {
            System.out.println("create refresh");
/*            ValueOperations<String, String> stringValueOperations = redisTemplate.opsForValue();
            String redisValue = stringValueOperations.get(refreshToken);
            if (redisValue != null) {*/
                return claims.getExpiration().after(new Date());
           // }
        }
        System.out.println("failed");
        return false;
    }

    public int getUserId(String jwt) {
        jwt = jwt.replace("Bearer ","");
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(jwt)
                .getBody();
        return (int)claims.get("id");
    }

    public List<String> getUserRole(String jwt) {
        jwt = jwt.replace("Bearer ","");
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(jwt)
                .getBody();
        return (List<String>)claims.get("role");
    }

}
