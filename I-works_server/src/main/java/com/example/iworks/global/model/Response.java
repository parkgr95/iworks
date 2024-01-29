package com.example.iworks.global.model;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Component
public class Response {
    public ResponseEntity<Map<String,Object>> handleSuccess(Object data){
        Map<String,Object> result = new HashMap<>();
        result.put("result","success");
        result.put("data",data);
        return new ResponseEntity<Map<String,Object>>(result, HttpStatus.OK);
    }

    public ResponseEntity<Map<String,Object>> handleError(Object data){
        Map<String,Object> result = new HashMap<>();
        result.put("result","failed");
        result.put("data",data);
        return new ResponseEntity<Map<String,Object>>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,Object>> exceptionHandler(Exception e){
        Map<String,Object> result = new HashMap<>();
        result.put("result","error");
        result.put("data",e.getMessage());
        return new ResponseEntity<Map<String,Object>>(result,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
