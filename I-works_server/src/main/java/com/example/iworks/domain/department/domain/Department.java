package com.example.iworks.domain.department.domain;

import com.example.iworks.domain.user.domain.User;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Data
@Builder @NoArgsConstructor @AllArgsConstructor
public class Department {

    @Id
    @GeneratedValue
    @Column(name="department_id")
    private Integer departmentId;

    @Column(name = "department_leader_id")
    private Integer departmentLeaderId; //부서 책임자 식별번호

    @Column(name = "department_name", nullable = false)
    private String departmentName; //부서 이름

    @Column(name = "department_desc")
    private String departmentDesc; //부서 설명

    @Column(name="department_tel_first")
    private String departmentTelFirst; //부서 대표번호 첫 필드

    @Column(name="department_tel_middle")
    private String departmentTelMiddle; //부서 대표번호 중간 필드

    @Column(name="department_tel_last")
    private String departmentTelLast; //부서 대표번호 끝 필드

    @Builder.Default
    @Column(name = "department_is_used", nullable = false)
    private boolean departmentIsUsed = true; // 사용 여부

    @Builder.Default
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "department_created_at", nullable = false)
    private LocalDateTime departmentCreatedAt = LocalDateTime.now() ; // 생성일시

    @Builder.Default
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "department_updated_at", nullable = false)
    private LocalDateTime departmentUpdatedAt = LocalDateTime.now(); // 최종 수정일시

    @Builder.Default
    @OneToMany(mappedBy = "userDepartment")
    private List<User> departmentUsers = new ArrayList<>();

    public void addUser(User user){
        this.departmentUsers.add(user);
        if(user.getUserDepartment() != this){
            user.setDepartment(this);
        }
    }
}
