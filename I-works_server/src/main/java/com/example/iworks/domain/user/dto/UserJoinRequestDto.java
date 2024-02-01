package com.example.iworks.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserJoinRequestDto implements Serializable {

    private int userDepartmentId; //부서
    private int userPositionCodeId; //직급 코드 아이디
    private String userEid; //사번
    private String userNameFirst; // 유저 이름
    private String userNameLast;
    private String userEmail; //유저 이메일
    private String userTel; //전화번호
    private String userAddress; //주소
    private String userGender; // 성별
}
