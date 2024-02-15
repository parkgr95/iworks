package com.example.iworks.domain.admin.controller;

import com.example.iworks.domain.admin.dto.adminDepartment.request.AdminDepartmentCreateRequestDto;
import com.example.iworks.domain.admin.dto.adminDepartment.request.AdminDepartmentUpdateRequestDto;
import com.example.iworks.domain.admin.service.adminDepartment.AdminDepartmentService;
import com.example.iworks.global.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/admin/department")
@RestController
@RequiredArgsConstructor
public class AdminDepartmentController {

    private final AdminDepartmentService adminService;
    private final Response response;

    // 부서 등록
    @PostMapping("/")
    public ResponseEntity<?> createDepartment(@RequestBody AdminDepartmentCreateRequestDto requestDto) {
        return adminService.createDepartment(requestDto);
    }

    // 부서 수정
    @PutMapping("/{departmentId}")
    public ResponseEntity<?> updateDepartment(@PathVariable(name = "departmentId") int departmentId, @RequestBody AdminDepartmentUpdateRequestDto requestDto) {
        return adminService.updateDepartment(departmentId, requestDto);
    }

    // 부서 전체 조회
    @GetMapping("/")
    public ResponseEntity<?> getDepartmentAll() {
        return response.handleSuccess(adminService.getDepartmentAll());
    }

    // 부서 세부 조회
    @GetMapping("/{departmentId}")
    public ResponseEntity<?> getDepartment(@PathVariable(name = "departmentId") int departmentId) {
        return adminService.getDepartment(departmentId);
    }
}