package com.example.iworks.user.model.entity;


import com.example.iworks.department.model.entity.Department;
import com.example.iworks.global.model.entity.Code;
import com.example.iworks.user.model.Status;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Access(AccessType.FIELD)
@Getter
@Data
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

    @Column(name = "user_name_first", nullable = false)
    private String userNameFirst; // 유저 이름

    @Column(name = "user_name_last", nullable = false)
    private String userNameLast;

    @Column(name = "user_email", nullable = false)
    private String userEmail; //유저 이메일

    @Column(name = "user_password", nullable = false, length = 200)
    private String userPassword; //비밀번호

    @Column(name = "user_tel", length = 12)
    private String userTel; //전화번호

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

    @Column(name = "user_is_deleted")
    private Boolean userIsDeleted; //탈퇴여부

    @Column(name = "user_role", nullable = false)
    private String userRole; //권한

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

    public List<String> getRoleList(){
        if(!this.userRole.isEmpty()){
            return Arrays.asList(this.userRole.split(","));
        }
        return new ArrayList<>();
    }

    public void setRoleList(ArrayList<String> roleList){
        StringBuilder sb = new StringBuilder();
        for(String role : roleList){
            sb.append(role).append(",");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        userRole = sb.toString();
    }

}