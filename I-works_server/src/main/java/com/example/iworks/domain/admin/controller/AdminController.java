package com.example.iworks.domain.admin.controller;

import com.example.iworks.domain.admin.dto.request.AdminDepartmentCreateRequestDto;
import com.example.iworks.domain.admin.dto.request.AdminDepartmentUpdateRequestDto;
import com.example.iworks.domain.admin.service.AdminService;
import com.example.iworks.global.model.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/admin/department")
@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final Response response;

    // 부서 등록
    @PostMapping("/")
    public ResponseEntity<?> createDepartment(@RequestBody AdminDepartmentCreateRequestDto requestDto) {
        adminService.createDepartment(requestDto);
        return response.handleSuccess("부서 등록 완료");
    }

    // 부서 수정
    @PutMapping("/{departmentId}")
    public ResponseEntity<?> updateDepartment(@PathVariable(name = "departmentId") int departmentId, @RequestBody AdminDepartmentUpdateRequestDto requestDto) {
        adminService.updateDepartment(departmentId, requestDto);
        return response.handleSuccess("부서 수정 완료");
    }

    // 부서 전체 조회
    @GetMapping("/")
    public ResponseEntity<?> getDepartmentAll() {
        return response.handleSuccess(adminService.getDepartmentAll());
    }

    // 부서 세부 조회
    @GetMapping("/{departmentId}")
    public ResponseEntity<?> getDepartment(@PathVariable(name = "departmentId") int departmentId) {
        return response.handleSuccess(adminService.getDepartment(departmentId));
    }
}