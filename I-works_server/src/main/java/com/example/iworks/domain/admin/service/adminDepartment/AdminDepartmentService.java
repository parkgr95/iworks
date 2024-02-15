package com.example.iworks.domain.admin.service.adminDepartment;

import com.example.iworks.domain.admin.dto.adminDepartment.request.AdminDepartmentCreateRequestDto;
import com.example.iworks.domain.admin.dto.adminDepartment.request.AdminDepartmentUpdateRequestDto;
import com.example.iworks.domain.admin.dto.adminDepartment.response.AdminDepartmentResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface AdminDepartmentService {

    // 부서 등록
    ResponseEntity<Map<String, Object>> createDepartment(AdminDepartmentCreateRequestDto requestDto);

    // 부서 수정
    ResponseEntity<Map<String, Object>> updateDepartment(int departmentId, AdminDepartmentUpdateRequestDto requestDto);

    // 부서 전체 조회
    ResponseEntity<Map<String, Object>> getDepartmentAll();

    // 부서 세부 조회
    ResponseEntity<Map<String, Object>> getDepartment(int departmentId);

}
