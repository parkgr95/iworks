package com.example.iworks.model.entity;

import com.example.iworks.model.Position;
import com.example.iworks.model.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Access(AccessType.FIELD)
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id", nullable = false) // 생략시 primitive type의 경우 not null로 생성
    private int id; //유저 아이디

    //부서아이디
    //직급 코드 아이디

    @Column(name = "user_eid", nullable = false)
    private String eid; //사번

    @Column(name = "user_name")
    private String name; // 유저 이름

    @Column(name = "user_email",nullable = false)
    private String email; //유저 이메일

    @Column(name = "user_password", nullable = false)
    private String password; //비밀번호

    @Column(name = "user_tel_first")
    private String telFirst; //전화번호 첫 필드

    @Column(name = "user_tel_middle")
    private String telMiddle; //전화번호 중간 필드

    @Column(name = "user_tel_last")
    private String telLast; //전화번호 끝 필드

    @Column(name = "user_address")
    private String address; //주소

    @Column(name = "user_gender")
    private String gender; // 성별

    /**
     * EnumType.STRING = 문자 그대로 저장
     * EnumType.ORDINAL = ENUM 순서를 저장
     */

    @Temporal(TemporalType.TIMESTAMP) // mysql :datetime , oracle : timestamp
    @Column(name = "user_created_at", nullable = false)
    private LocalDateTime createdAt; // 가입일시

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "user_updated_at", nullable = false)
    private LocalDateTime updatedAt; // 회원 정보 수정 일시

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "user_deleted_at")
    private LocalDateTime userDeletedAt; // 탈퇴일시

    @Column(name = "user_is_deleted", nullable = false)
    private String isDeleted; //탈퇴여부

    @Column(name = "user_is_admin", nullable = false)
    private String isAdmin; //관리자여부

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "user_status")
    private Status status; // 상태

    @ManyToOne
    @JoinColumn(name="dept_id")
    private Department department; //부서

    public User(){} //기본 생성자 필수

    public void setDepartment(Department department){
        this.department = department;
        if(!department.getUsers().contains(this)){
            department.getUsers().add(this);
        }
    }
}