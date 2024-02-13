package com.example.iworks.domain.user.dto;

import com.example.iworks.domain.user.domain.User;
import lombok.Getter;

@Getter
public class UserGetMyPageResponseDto {
    private final int userId; //유저 아이디
    private final int departmentId;
    private final String departmentName; //부서
    private final String userPosition; //직급 코드 아이디
    private final String userEid; //사번
    private final String userNameFirst; // 유저 이름
    private final String userNameLast;
    private final String userEmail; //유저 이메일
    private final String userTel; //전화번호
    private final String userAddress; //주소
    private final String userGender; // 성별

    public UserGetMyPageResponseDto(User user){
        this.userId = user.getUserId();
        this.departmentId = user.getUserDepartment().getDepartmentId();
        this.departmentName = user.getUserDepartment().getDepartmentName();
        this.userPosition = user.getUserPositionCode().getCodeName();
        this.userEid = user.getUserEid();
        this.userNameFirst = user.getUserNameFirst();
        this.userNameLast = user.getUserNameLast();
        this.userEmail = user.getUserEmail();
        this.userTel = user.getUserTel();
        this.userAddress = user.getUserAddress();
        this.userGender = user.getUserGender();
    }
}
