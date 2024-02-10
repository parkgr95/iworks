package com.example.iworks.domain.code.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface CodeService {

    ResponseEntity<Map<String,Object>> getPositionCodeAll();
    ResponseEntity<Map<String,Object>> getCategoryCodeAll();
    ResponseEntity<Map<String,Object>> getStatusCodeAll();
    ResponseEntity<Map<String,Object>> getTargetCodeAll();
}
