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
    @PostMapping("/join")
    public ResponseEntity<?> joinUser(@RequestBody AdminUserCreateRequestDto requestDto) {
        return adminService.registerUser(requestDto);
    }

    // 유저 전체 조회
    @GetMapping("/")
    public ResponseEntity<?> getUserAll() {
        return adminService.getUserAll();
    }

    // 유저 상세 조회
    @GetMapping("/detail/{userId}")
    public ResponseEntity<?> getUser(@PathVariable(name = "userId") int userId) {
        return adminService.getUser(userId);
    }

    // 유저 정보 수정
    @PutMapping("/update/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable(name = "userId") int userId, AdminUserUpdateRequestDto requestDto) {
        return adminService.updateUser(userId, requestDto);
    }

    // 유저 삭제
    @PutMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable(name = "userId") int userId) {
        return adminService.deleteUser(userId);
    }
}
