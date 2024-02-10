package com.example.iworks.domain.leader.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface LeaderService {
    ResponseEntity<Map<String,Object>> deleteBoard(int boardId,String token);
}
