package com.example.iworks.domain.admin.service.adminUser;

import com.example.iworks.domain.admin.dto.adminUser.request.AdminUserCreateRequestDto;
import com.example.iworks.domain.admin.dto.adminUser.request.AdminUserUpdateRequestDto;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface AdminUserService {
    // 유저 생성
    ResponseEntity<Map<String, Object>> registerUser(AdminUserCreateRequestDto dto);

    // 유저 전체 조회
    ResponseEntity<Map<String, Object>> getUserAll();

    // 유저 상세 정보 조회
    ResponseEntity<Map<String, Object>> getUser(int userId);

    // 유저 정보 수정
    ResponseEntity<Map<String, Object>> updateUser(int userId, AdminUserUpdateRequestDto dto);

    // 유저 삭제
    ResponseEntity<Map<String, Object>> deleteUser(int userId);
}
