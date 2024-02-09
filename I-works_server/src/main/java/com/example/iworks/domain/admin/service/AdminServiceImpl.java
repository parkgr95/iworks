package com.example.iworks.domain.admin.service;

import com.example.iworks.domain.admin.dto.request.AdminDepartmentCreateRequestDto;
import com.example.iworks.domain.admin.dto.request.AdminDepartmentUpdateRequestDto;
import com.example.iworks.domain.admin.dto.response.AdminDepartmentResponseDto;
import com.example.iworks.domain.department.domain.Department;
import com.example.iworks.domain.department.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AdminServiceImpl implements AdminService{

    private final DepartmentRepository departmentRepository;

    private final PageRequest pageRequest = PageRequest.of(0, 10);

    @Transactional
    @Override
    public void createDepartment(AdminDepartmentCreateRequestDto requestDto) {
        departmentRepository.save(requestDto.toEntity());
    }

    @Transactional
    @Override
    public void updateDepartment(int departmentId, AdminDepartmentUpdateRequestDto requestDto) {
        Department findDepartment = departmentRepository.findById(departmentId)
                .orElseThrow(IllegalStateException::new);
        findDepartment.update(requestDto);
    }

    @Override
    public List<AdminDepartmentResponseDto> getDepartmentAll() {
        return departmentRepository.findAll(pageRequest)
                .stream()
                .map(AdminDepartmentResponseDto::new)
                .collect(toList());
    }

    @Override
    public AdminDepartmentResponseDto getDepartment(int departmentId) {
        return departmentRepository.findById(departmentId)
                .map(AdminDepartmentResponseDto::new)
                .orElseThrow(IllegalStateException::new);
    }
}
