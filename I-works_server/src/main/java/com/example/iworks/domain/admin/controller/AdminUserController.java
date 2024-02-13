package com.example.iworks.domain.admin.controller;

import com.example.iworks.domain.admin.dto.adminUser.request.AdminUserCreateRequestDto;
import com.example.iworks.domain.admin.dto.adminUser.request.AdminUserUpdateRequestDto;
import com.example.iworks.domain.admin.service.adminUser.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/admin/user")
@RestController
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminUserService adminService;

    // 유저 등록
    @PostMapping("/")
    public ResponseEntity<?> joinUser(@RequestBody AdminUserCreateRequestDto requestDto) {
        return adminService.registerUser(requestDto);
    }

    // 유저 전체 조회
    @GetMapping("/")
    public ResponseEntity<?> getUserAll() {
        return adminService.getUserAll();
    }

    // 유저 상세 조회
    @GetMapping("/detail")
    public ResponseEntity<?> getUser(@RequestHeader("Authorization") String token) {
        return adminService.getUser(token);
    }

    // 유저 정보 수정
    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestHeader("Authorization") String token, AdminUserUpdateRequestDto requestDto) {
        return adminService.updateUser(token, requestDto);
    }

    // 유저 삭제
    @PutMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestHeader("Authorization") String token) {
        return adminService.deleteUser(token);
    }
}
