package com.example.iworks.global.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.iworks.global.config.auth.PrincipalDetails;
import com.example.iworks.user.model.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Date;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    // /login 요청을 하면 로그인 시도를 위해서 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("attempt login");

        // 1. username, password 받기
        try {
            ObjectMapper om = new ObjectMapper();
            User user = om.readValue(request.getInputStream(),User.class);
            System.out.println(user);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserEid(),user.getUserPassword());
            System.out.println(authenticationToken);
            // PrincipalDetailsService의 loadUserByUsername()이 실행됨.
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            // authentication이 들어왔다는건 DB에 username과 password가 일치하는 것을 찾았다는 것.

            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            System.out.println(principalDetails.getUser().getUserName());
            // 이제 Authentication 객체가 세션영역에 저장됨. 즉, 로그인이 되었다는 뜻.
            return authentication; // 저장을 위해 리턴

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    // attemptAuthentication 후 인증이 정상적으로 되었다면 해당 함수가 실행
    // JWT 토큰을 생성해서 request를 요청한 사용자에게 JWT을 응답으로 주면 됨.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("successfulAuthentication :  인증 완료!" );

        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        //Hash암호 방식
        String jwtToken = JWT.create()
                .withSubject("token")
                .withExpiresAt(new Date(System.currentTimeMillis()+(60000*10))) // 토큰의 만료시간 10분
                .withClaim("eid",principalDetails.getUser().getUserEid())
                .withClaim("email",principalDetails.getUser().getUserEmail())
                .sign(Algorithm.HMAC512("secret"));

        response.addHeader("Authorization","Bearer "+jwtToken);
    }
}
