package com.example.iworks.domain.address.dto.response;

import com.example.iworks.domain.address.entity.Address;
import lombok.Getter;

@Getter
public class AddressUserResponseDto {
    private final int userId;
    private final String userEid;
    private final String userNameFirst;
    private final String userNameLast;
    private final String departmentName;
    private final Integer departmentId;
    private final String positionCodeName;
    private final Integer positionCodeId;
    private final String userTel;
    private final String userEmail;

    public AddressUserResponseDto(Address dto) {
        this.userId = dto.getUser().getUserId();
        this.userEid = dto.getUser().getUserEid();
        this.userNameFirst=dto.getUser().getUserNameFirst();
        this.userNameLast =dto.getUser().getUserNameLast();
        this.departmentName = dto.getDepartment()==null? null:dto.getDepartment().getDepartmentName();
        this.departmentId = dto.getDepartment()==null? null:dto.getDepartment().getDepartmentId();
        this.positionCodeName = dto.getCode()==null? null:dto.getCode().getCodeName();
        this.positionCodeId = dto.getCode()==null? null:dto.getCode().getCodeId();
        this.userTel = dto.getUser().getUserTel();
        this.userEmail = dto.getUser().getUserEmail();
    }
}
