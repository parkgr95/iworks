package com.example.iworks.global.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class JwtExceptionFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // 다음 filter Chain에 대한 실행 (filter-chain의 마지막에는 Dispatcher Servlet이 실행된다.)
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {

            //토큰의 유효기간 만료
            Map<String,Object> result = new HashMap<>();
            result.put("result","expired");
            result.put("data",e);
            response.getWriter().write(new ObjectMapper().writeValueAsString(result));

        } catch (JwtException | IllegalArgumentException e) {

            //유효하지 않은 토큰
            Map<String,Object> result = new HashMap<>();
            result.put("result","failed");
            result.put("data",e);
            response.getWriter().write(new ObjectMapper().writeValueAsString(result));

        } catch (NoSuchElementException e) {

            //사용자 찾을 수 없음
            Map<String,Object> result = new HashMap<>();
            result.put("result","failed");
            result.put("data",e);
            response.getWriter().write(new ObjectMapper().writeValueAsString(result));

        } catch (ArrayIndexOutOfBoundsException e) {

            Map<String,Object> result = new HashMap<>();
            result.put("result","failed");
            result.put("data",e);
            response.getWriter().write(new ObjectMapper().writeValueAsString(result));

        } catch (NullPointerException e) {

            filterChain.doFilter(request, response);
        }
    }

}
