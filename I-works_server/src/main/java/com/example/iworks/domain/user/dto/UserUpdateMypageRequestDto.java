package com.example.iworks.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateMypageRequestDto {
    private String userEmail; //유저 이메일
    private String userTel; //전화번호
    private String userAddress; //주소
    private String userPassword;
}
