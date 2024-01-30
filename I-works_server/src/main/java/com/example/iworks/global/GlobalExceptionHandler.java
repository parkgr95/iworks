package com.example.iworks.global;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.iworks.global.model.Response;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Response response;

    public GlobalExceptionHandler(Response response) {
        this.response = response;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,Object>> exceptionHandler(Exception e){
        Map<String,Object> result = new HashMap<>();
        result.put("result","error");
        result.put("data",e.getMessage());
        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Map<String,Object>> handleExpiredJwtException(ExpiredJwtException e){
        Map<String,Object> result = new HashMap<>();
        result.put("result","expired");
        result.put("data",e);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<Map<String,Object>> handleJWTVerificationException(JWTVerificationException e){
        return response.handleFail(e);
    }
}
