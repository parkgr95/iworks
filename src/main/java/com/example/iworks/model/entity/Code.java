package com.example.iworks.model.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.Group;

@Entity
@Table(name = "code")
@Getter
@EqualsAndHashCode
public class Code {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_id", nullable = false)
    private int codeId; // 코드 아이디

    @Column(name = "code_name", length = 30, nullable = false)
    private String codeName; // 코드명

    @Column(name = "code_is_use", nullable = false)
    private boolean codeIsUse; // 코드 사용 여부

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
