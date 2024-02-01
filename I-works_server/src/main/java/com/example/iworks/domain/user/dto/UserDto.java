package com.example.iworks.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserDto implements Serializable {
    private int userId;
    private String userName;
    private String departmentName;
    private int departmentId;

}
