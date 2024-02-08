package com.example.iworks.global.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "code")
@Getter @Setter
@EqualsAndHashCode
@Builder @AllArgsConstructor @NoArgsConstructor
public class Code {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_id", nullable = false)
    private Integer codeId; // 코드 아이디

    @Column(name="code_code",nullable = false,unique = true)
    private Integer codeCode;

    @Column(name = "code_name", length = 30, nullable = false)
    private String codeName; // 코드명

    @Builder.Default
    @Column(name = "code_is_use", nullable = false)
    private boolean codeIsUse = true; // 코드 사용 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_group_id", nullable = false)
    private CodeGroup codeCodeGroup; // 코드 그룹 아이디 (외래키)

    public void setCodeGroup(CodeGroup codeGroup){
        this.codeCodeGroup = codeGroup;
        if (codeGroup.getCodeGroupCodes() != this){
            codeGroup.setCodes(this);
        }
    }
}
