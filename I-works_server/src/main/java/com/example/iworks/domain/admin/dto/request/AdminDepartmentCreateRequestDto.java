package com.example.iworks.domain.admin.dto.request;

import com.example.iworks.domain.department.domain.Department;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AdminDepartmentCreateRequestDto {
    private int departmentLeaderId; // 부서 책임자 아이디
    private String departmentName; //  부서명
    private String departmentDesc; // 부서 설명
    private String departmentTelFirst; // 부서 대표번호 첫 필드
    private String departmentTelMiddle; // 부서 대표번호 중간 필드
    private String departmentTelLast; // 부서 대표번호 끝 필드

    public Department toEntity() {
        return Department.builder()
                .departmentLeaderId(departmentLeaderId)
                .departmentName(departmentName)
                .departmentDesc(departmentDesc)
                .departmentTelFirst(departmentTelFirst)
                .departmentTelMiddle(departmentTelMiddle)
                .departmentTelLast(departmentTelLast)
                .departmentIsUsed(true)
                .departmentCreatedAt(LocalDateTime.now())
                .departmentUpdatedAt(LocalDateTime.now())
                .build();
    }
}
