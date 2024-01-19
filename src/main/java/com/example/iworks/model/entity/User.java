package com.example.iworks.model.entity;


import com.example.iworks.model.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Access(AccessType.FIELD)
@Getter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id", nullable = false) // 생략시 primitive type의 경우 not null로 생성
    private int userId; //유저 아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_department_id")
    private Department userDepartment; //부서

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_position_code_id", referencedColumnName = "code_id", insertable = false, updatable = false)
    private Code userPositionCode; //직급 코드 아이디

    @Column(name = "user_eid", nullable = false)
    private String userEid; //사번

    @Column(name = "user_name")
    private String userName; // 유저 이름

    @Column(name = "user_email", nullable = false)
    private String userEmail; //유저 이메일

    @Column(name = "user_password", nullable = false)
    private String userPassword; //비밀번호

    @Column(name = "user_tel_first")
    private String userTelFirst; //전화번호 첫 필드

    @Column(name = "user_tel_middle")
    private String userTelMiddle; //전화번호 중간 필드

    @Column(name = "user_tel_last")
    private String userTelLast; //전화번호 끝 필드

    @Column(name = "user_address")
    private String userAddress; //주소

    @Column(name = "user_gender")
    private String userGender; // 성별

    /**
     * EnumType.STRING = 문자 그대로 저장
     * EnumType.ORDINAL = ENUM 순서를 저장
     */

    @Temporal(TemporalType.TIMESTAMP) // mysql :datetime , oracle : timestamp
    @Column(name = "user_created_at", nullable = false)
    private LocalDateTime userCreatedAt; // 가입일시

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "user_updated_at", nullable = false)
    private LocalDateTime userUpdatedAt; // 회원 정보 수정 일시

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "user_deleted_at")
    private LocalDateTime userDeletedAt; // 탈퇴일시

    @Column(name = "user_is_deleted", nullable = false)
    private boolean userIsDeleted; //탈퇴여부

    @Column(name = "user_is_admin", nullable = false)
    private boolean userIsAdmin; //관리자여부

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "user_status")
    private Status userStatus; // 상태

    public User() {
    } //기본 생성자 필수

    public void setDepartment(Department department){
        this.userDepartment = department;
        if(!department.getDepartmentUsers().contains(this)){
            department.getDepartmentUsers().add(this);
        }
    }
}