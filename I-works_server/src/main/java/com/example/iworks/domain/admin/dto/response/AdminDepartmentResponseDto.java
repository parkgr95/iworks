package com.example.iworks.domain.admin.dto.response;

import com.example.iworks.domain.department.domain.Department;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AdminDepartmentResponseDto {
    private int departmentId; // 부서 아이디
    private int departmentLeaderId; // 부서 책임자 아이디
    private String departmentName; //  부서명
    private String departmentDesc; // 부서 설명
    private String departmentTelFirst; // 부서 대표번호 첫 필드
    private String departmentTelMiddle; // 부서 대표번호 중간 필드
    private String departmentTelLast; // 부서 대표번호 끝 필드
    private Boolean departmentIsUsed; // 부서 사용 여부
    private LocalDateTime departmentCreatedAt; // 부서 생성 일시
    private LocalDateTime departmentUpdatedAt; // 부서 최종 수정 일시

    public AdminDepartmentResponseDto(Department department) {
        this.departmentId = department.getDepartmentId();
        this.departmentLeaderId = department.getDepartmentLeaderId();
        this.departmentName = department.getDepartmentName();
        this.departmentDesc = department.getDepartmentDesc();
        this.departmentTelFirst = department.getDepartmentTelFirst();
        this.departmentTelMiddle = department.getDepartmentTelMiddle();
        this.departmentTelLast = department.getDepartmentTelLast();
        this.departmentIsUsed = department.isDepartmentIsUsed();
        this.departmentCreatedAt = department.getDepartmentCreatedAt();
        this.departmentUpdatedAt = department.getDepartmentUpdatedAt();
    }
}
