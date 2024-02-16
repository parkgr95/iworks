package com.example.iworks.domain.admin.service.adminDepartment;

import com.example.iworks.domain.admin.dto.adminDepartment.request.AdminDepartmentCreateRequestDto;
import com.example.iworks.domain.admin.dto.adminDepartment.request.AdminDepartmentUpdateRequestDto;
import com.example.iworks.domain.admin.dto.adminDepartment.response.AdminDepartmentResponseDto;
import com.example.iworks.domain.board.dto.response.BoardGetResponseDto;
import com.example.iworks.domain.department.domain.Department;
import com.example.iworks.domain.department.repository.DepartmentRepository;
import com.example.iworks.global.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AdminDepartmentServiceImpl implements AdminDepartmentService {

    private final DepartmentRepository departmentRepository;
    private final Response response;

    private final PageRequest pageRequest = PageRequest.of(0, 10);

    @Transactional
    @Override
    public ResponseEntity<Map<String, Object>> createDepartment(AdminDepartmentCreateRequestDto requestDto) {
        try {
            departmentRepository.save(requestDto.toEntity());
            return response.handleSuccess("부서 등록 완료");
        } catch (Exception e) {
            return response.handleFail("부서 등록 실패", e.getMessage());
        }
    }

    @Transactional
    @Override
    public ResponseEntity<Map<String, Object>> updateDepartment(int departmentId, AdminDepartmentUpdateRequestDto requestDto) {

            Department findDepartment = departmentRepository.findByDepartmentId(departmentId);
            if(findDepartment == null) {
                return response.handleFail("잘못된 부서 입력",null);
            }

            findDepartment.update(requestDto);
            return response.handleSuccess("부서 수정 완료");
    }

    @Override
    public ResponseEntity<Map<String, Object>> getDepartmentAll() {
        Stream<AdminDepartmentResponseDto> result = departmentRepository.findAll(pageRequest)
                .stream()
                .map(AdminDepartmentResponseDto::new);
        return response.handleSuccess(result);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getDepartment(int departmentId) {

        Department findDepartment = departmentRepository.findByDepartmentId(departmentId);
        if(findDepartment == null) {
            return response.handleFail("잘못된 부서 입력",null);
        }

        AdminDepartmentResponseDto result = new AdminDepartmentResponseDto(findDepartment);
        return response.handleSuccess(result);

    }
}