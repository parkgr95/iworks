package com.example.iworks.domain.admin.dto.adminUser.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserUpdateRequestDto {
    private int userDepartmentId; // 부서 아이디
    private int userPositionCodeId; // 직급 코드 아이디
    private String userEid; // 사번
    private String userNameFirst; // 유저 이름
    private String userNameLast; // 유저 성
    private String userEmail; // 이메일
    private String userPassword; // 비밀번호
    private String userTel; // 전화번호
    private String userAddress; // 주소
    private String userGender; // 성별
    private String userRole; // 권한
}
