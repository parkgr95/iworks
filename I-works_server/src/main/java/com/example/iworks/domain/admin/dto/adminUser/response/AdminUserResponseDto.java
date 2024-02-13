package com.example.iworks.domain.admin.dto.adminUser.response;

import com.example.iworks.domain.user.domain.Status;
import com.example.iworks.domain.user.domain.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AdminUserResponseDto {
    private int userId; // 유저 아이디
    private final String departmentName; // 부서
    private final String userPosition; // 직급 코드 아이디
    private final String userEid; // 사번
    private final String userNameFirst; // 유저 이름
    private final String userNameLast; // 유저 성
    private final String userEmail; // 이메일
    private final String userTel; // 전화번호
    private final String userAddress; // 주소
    private final String userGender; // 성별
    private final LocalDateTime userCreatedAt; // 가입일시
    private final LocalDateTime userUpdatedAt; // 수정일시
    private final LocalDateTime userDeletedAt; // 탈퇴일시
    private final Boolean userIsDeleted; // 탈퇴여부
    private final String userRole; // 권한
    private final Status userStaus; // 상태

    public AdminUserResponseDto(User user){
        this.userId = user.getUserId();
        this.departmentName = user.getUserDepartment().getDepartmentName();
        this.userPosition = user.getUserPositionCode().getCodeName();
        this.userEid = user.getUserEid();
        this.userNameFirst = user.getUserNameFirst();
        this.userNameLast = user.getUserNameLast();
        this.userEmail = user.getUserEmail();
        this.userTel = user.getUserTel();
        this.userAddress = user.getUserAddress();
        this.userGender = user.getUserGender();
        this.userCreatedAt = user.getUserCreatedAt();
        this.userUpdatedAt = user.getUserUpdatedAt();
        this.userDeletedAt = user.getUserDeletedAt();
        this.userIsDeleted = user.getUserIsDeleted();
        this.userRole = user.getUserRole();
        this.userStaus = user.getUserStatus();
    }
}
