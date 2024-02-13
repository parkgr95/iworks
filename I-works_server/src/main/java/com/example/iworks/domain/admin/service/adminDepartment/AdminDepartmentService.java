package com.example.iworks.domain.admin.service.adminDepartment;

import com.example.iworks.domain.admin.dto.adminDepartment.request.AdminDepartmentCreateRequestDto;
import com.example.iworks.domain.admin.dto.adminDepartment.request.AdminDepartmentUpdateRequestDto;
import com.example.iworks.domain.admin.dto.adminDepartment.response.AdminDepartmentResponseDto;

import java.util.List;

public interface AdminDepartmentService {

    // 부서 등록
    void createDepartment(AdminDepartmentCreateRequestDto requestDto);

    // 부서 수정
    void updateDepartment(int departmentId, AdminDepartmentUpdateRequestDto requestDto);

    // 부서 전체 조회
    List<AdminDepartmentResponseDto> getDepartmentAll();

    // 부서 세부 조회
    AdminDepartmentResponseDto getDepartment(int departmentId);

}
