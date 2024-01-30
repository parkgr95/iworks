package com.example.iworks.global.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Response {
    public String getSuccessString(Object data) throws JsonProcessingException {
        Map<String,Object> result = new HashMap<>();
        result.put("result","success");
        result.put("data",data);
        return new ObjectMapper().writeValueAsString(result);
    }
    public String getFailString(Object data) throws JsonProcessingException {
        Map<String,Object> result = new HashMap<>();
        result.put("result","failed");
        result.put("data",data);
        return new ObjectMapper().writeValueAsString(result);
    }

    public ResponseEntity<Map<String,Object>> handleSuccess(Object data){
        Map<String,Object> result = new HashMap<>();
        result.put("result","success");
        result.put("data",data);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    public ResponseEntity<Map<String,Object>> handleFail(Object data){
        Map<String,Object> result = new HashMap<>();
        result.put("result","failed");
        result.put("data",data);
        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
