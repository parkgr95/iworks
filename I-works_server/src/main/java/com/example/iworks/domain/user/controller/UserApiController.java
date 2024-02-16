package com.example.iworks.domain.user.controller;

import com.example.iworks.domain.user.dto.UserUpdateMypageRequestDto;
import com.example.iworks.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/api/user")
@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @GetMapping("/mypage")
    public ResponseEntity<Map<String,Object>> getMypage(@RequestHeader("Authorization") String token){
        return userService.selectUser(token);
    }
    @PutMapping("/mypage")
    public ResponseEntity<Map<String,Object>> updateMypage(@RequestHeader("Authorization") String token,@RequestBody UserUpdateMypageRequestDto dto){
        return userService.updateUser(token, dto);
    }

}