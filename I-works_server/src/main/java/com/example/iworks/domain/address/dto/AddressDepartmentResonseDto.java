package com.example.iworks.domain.address.dto;

import com.example.iworks.domain.department.domain.Department;
import lombok.Getter;

@Getter
public class AddressDepartmentResonseDto {
    private final String departmentName;
    private final Integer departmentId;

    public AddressDepartmentResonseDto(Department department){
        this.departmentId=department.getDepartmentId();
        this.departmentName=department.getDepartmentName();
    }
}
