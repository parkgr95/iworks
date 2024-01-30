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

public class JwtExceptionFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request,response);
        }catch (ExpiredJwtException e){
            Map<String,Object> result = new HashMap<>();
            result.put("result","expired");
            result.put("data",e);
            response.getWriter().write(new ObjectMapper().writeValueAsString(result));
        }catch(JwtException e){
            Map<String,Object> result = new HashMap<>();
            result.put("result","failed");
            result.put("data",e);
            response.getWriter().write(new ObjectMapper().writeValueAsString(result));
        }
    }

}
