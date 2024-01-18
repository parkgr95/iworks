package com.example.iworks.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "code_group")
@Getter @Setter
public class CodeGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_group_id", nullable = false)
    private int id; // 코드 그룹 아이디

    @Column(name = "code_group_name", length = 30, nullable = false)
    private String name; // 코드 그룹 이름

    @Column(name = "code_group_is_used", nullable = false)
    private boolean isUsed; // 코드 그룹 사용 여부

}
