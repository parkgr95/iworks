package com.example.iworks.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "code")
@Getter
@Setter
public class Code {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_id", nullable = false)
    private int id; // 코드 아이디

    @Column(name = "code_name", length = 30, nullable = false)
    private String name; // 코드명

    @Column(name = "code_is_use", nullable = false)
    private boolean isUse; // 코드 사용 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_group_id", nullable = false)
    private CodeGroup codeGroup; // 코드 그룹 아이디 (외래키)

}
