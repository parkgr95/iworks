package com.example.iworks.domain.user.domain;


import com.example.iworks.domain.department.domain.Department;
import com.example.iworks.domain.team.domain.TeamUser;
import com.example.iworks.domain.user.dto.UserJoinRequestDto;
import com.example.iworks.domain.user.dto.UserUpdateMypageRequestDto;
import com.example.iworks.global.model.entity.Code;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Getter
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private int userId; //유저 아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_department_id")
    private Department userDepartment; //부서

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_position_code_id", referencedColumnName = "code_id", insertable = false, updatable = false)
    private Code userPositionCode; //직급 코드 아이디

    @Builder.Default
    @Column(name = "user_eid", nullable = false)
    private String userEid = "240001"; //사번

    @Builder.Default
    @Column(name = "user_name_first", nullable = false)
    private String userNameFirst = "work"; // 유저 이름

    @Builder.Default
    @Column(name = "user_name_last", nullable = false)
    private String userNameLast = "i";

    @Builder.Default
    @Column(name = "user_email", nullable = false, unique = true)
    private String userEmail = "11111@naver.com"; //유저 이메일

    @Column(name = "user_password", nullable = false, length = 200)
    private String userPassword; //비밀번호

    @Column(name = "user_tel", length = 12)
    private String userTel; //전화번호

    @Column(name = "user_address")
    private String userAddress; //주소

    @Column(name = "user_gender")
    private String userGender; // 성별

    @Builder.Default
    @Temporal(TemporalType.TIMESTAMP) // mysql :datetime , oracle : timestamp
    @Column(name = "user_created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime userCreatedAt = LocalDateTime.now(); // 가입일시

    @Builder.Default
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "user_updated_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime userUpdatedAt = LocalDateTime.now(); // 회원 정보 수정 일시

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "user_deleted_at")
    private LocalDateTime userDeletedAt; // 탈퇴일시

    @Column(name = "user_is_deleted")
    private Boolean userIsDeleted; //탈퇴여부

    @Builder.Default
    @Column(name = "user_role", nullable = false)
    private String userRole = "USER,ADMIN"; //권한

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "user_status")
    private Status userStatus; // 상태

    @OneToMany(mappedBy = "teamUserUser")
    private List<TeamUser> userTeamUsers = new ArrayList<>(); // 맴버별 팀유저

    public User(UserJoinRequestDto dto){
         this.userEid = dto.getUserEid();
         this.userNameFirst =dto.getUserNameFirst();
         this.userNameLast = dto.getUserNameLast();
         this.userEmail = dto.getUserEmail();
         this.userTel = dto.getUserTel();
         this.userAddress = dto.getUserAddress();
         this.userGender = dto.getUserGender();
         this.userCreatedAt = LocalDateTime.now();
         this.userUpdatedAt = LocalDateTime.now();
    }

    public void setRandomPassword(String password){
        this.userPassword = password;
    }

    public void setPositionCode(Code code){
        this.userPositionCode = code;
    }

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

    public void update(UserUpdateMypageRequestDto dto, BCryptPasswordEncoder encoder) {
        if(dto.getUserAddress() != null){
            this.userAddress = dto.getUserAddress();
        }
        if(dto.getUserEmail() != null){
            this.userEmail = dto.getUserEmail();
        }
        if(dto.getUserTel() != null){
            this.userTel = dto.getUserTel();
        }
        if(dto.getUserPassword() != null){
            this.userPassword = encoder.encode(dto.getUserPassword());
        }
        this.userUpdatedAt = LocalDateTime.now();
    }

    public String getUserName(){
        return this.userNameFirst +" "+this.userNameLast;
    }

}