package com.example.iworks.domain.code.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "code_group")
@Getter
@Builder @AllArgsConstructor @NoArgsConstructor
@ToString
public class CodeGroup {

    @Id
    @GeneratedValue
    @Column(name = "code_group_id", nullable = false)
    private Integer codeGroupId; // 코드 그룹 아이디

    @Column(name = "code_group_name", length = 30, nullable = false)
    private String codeGroupName; // 코드 그룹 이름

    @Builder.Default
    @Column(name = "code_group_is_used", nullable = false)
    private boolean codeGroupIsUsed = true; // 코드 그룹 사용 여부

    @Builder.Default
    @OneToMany(mappedBy = "codeCodeGroup")
    private List<Code> codeGroupCodes = new ArrayList<>();

    public void setCodes(Code code){ //if code.group == this if code.setCodeGroup(this)
        this.codeGroupCodes.add(code);
        if(code.getCodeCodeGroup() != this){
            code.setCodeGroup(this);
        }
    }
}
