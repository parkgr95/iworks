package com.example.iworks.user.controller;

import com.example.iworks.user.model.entity.Code;
import com.example.iworks.user.model.entity.Department;
import com.example.iworks.user.model.entity.User;
import com.example.iworks.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

@RequestMapping("/api/user")
@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    @GetMapping("home")
    public String home() {
        return "<h1>home</h1>";
    }

    @PostMapping("token")
    public String token() {
        return "<h1>token</h1>";
    }

    @PostMapping("join")
    public String join(@RequestBody User user) {
        System.out.println(user);

        Department dept = new Department();
        dept.setDepartmentId(1);
        dept.setDepartmentName("testDept");
        Code code = new Code();
        code.setCodeId(1);
        user.setUserPositionCode(code);
        user.setUserCreatedAt(LocalDateTime.now());
        user.setUserUpdatedAt(LocalDateTime.now());
        ArrayList<String> roleList = new ArrayList<>();
        int pCode = user.getUserPositionCode().getCodeId();
        if(pCode == 1){
            roleList.add("ROLE_CEO");
        } else if(pCode==2){
            roleList.add("ROLE_LEADER");
        } else{
            roleList.add("ROLE_EMPLOYEE");
        }
        user.setUserPassword(bCryptPasswordEncoder.encode(user.getUserPassword()));
        user.setRoleList(roleList);
        userRepository.save(user);
        return "join success";
    }
}