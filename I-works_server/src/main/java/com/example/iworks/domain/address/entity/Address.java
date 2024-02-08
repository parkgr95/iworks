package com.example.iworks.domain.address.entity;

import com.example.iworks.domain.department.domain.Department;
import com.example.iworks.domain.user.domain.User;
import com.example.iworks.domain.code.entity.Code;


public interface Address {
    User getUser();
    Department getDepartment();
    Code getCode();
}
