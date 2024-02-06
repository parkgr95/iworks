package com.example.iworks.domain.address.dto;

import com.example.iworks.domain.department.domain.Department;
import com.example.iworks.domain.user.domain.User;
import com.example.iworks.global.model.entity.Code;


public interface AddressDto {
    User getUser();
    Department getDepartment();
    Code getCode();
}
