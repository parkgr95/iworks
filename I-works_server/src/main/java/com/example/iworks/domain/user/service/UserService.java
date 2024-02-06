package com.example.iworks.domain.user.service;

import com.example.iworks.domain.user.dto.UserJoinRequestDto;
import com.example.iworks.domain.user.dto.UserUpdateMypageRequestDto;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface UserService {
    ResponseEntity<Map<String, Object>> registerUser(UserJoinRequestDto dto);
    ResponseEntity<Map<String, Object>> selectUser(String token);
    ResponseEntity<Map<String, Object>> updateUser(String token, UserUpdateMypageRequestDto dto);
}
