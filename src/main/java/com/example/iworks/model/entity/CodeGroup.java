package com.example.iworks.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "code_group")
@Getter
public class CodeGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_group_id", nullable = false)
    private int id; // 코드 그룹 아이디

    @Column(name = "code_group_name", length = 30, nullable = false)
    private String name; // 코드 그룹 이름

    @Column(name = "code_group_is_used", nullable = false)
    private boolean isUsed; // 코드 그룹 사용 여부

    @OneToMany(mappedBy = "codeGroup")
    private List<Code> codes = new ArrayList<>();

    public void setCodes(Code code){ //if code.group == this if code.setCodeGroup(this)
        this.codes.add(code);
        if(code.getCodeGroup() != this){
            code.setCodeGroup(this);
        }
    }
}
