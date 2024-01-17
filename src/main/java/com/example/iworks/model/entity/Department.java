package com.example.iworks.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="dept_id", nullable = false )
    private int id;

    @Column(name = "dept_leader_id", nullable = false)
    private int leaderId; //부서 책임자 식별번호

    @Column(name = "dept_name", nullable = false)
    private String name; //부서 이름

    @Column(name = "dept_desc")
    private String desc; //부서 설명

    @Column(name="dept_tel")
    private String tel; //부서 대표번호

    @OneToMany(mappedBy = "department")
    private List<User> users = new ArrayList<User>();

    public void addUser(User user){
        this.users.add(user);
        if(user.getDepartment() != this){
            user.setDepartment(this);
        }
    }
}
