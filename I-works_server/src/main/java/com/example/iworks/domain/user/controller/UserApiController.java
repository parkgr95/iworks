package com.example.iworks.domain.user.controller;

import com.example.iworks.domain.department.domain.Department;
import com.example.iworks.domain.user.domain.User;
import com.example.iworks.global.model.entity.Code;
import com.example.iworks.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/api/user")
@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    @GetMapping("/home")
    public String home() {
        return "<h1>home</h1>";
    }


    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody User user) {
        System.out.println(user);
        return null;
    }

    @PostMapping("/join")
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

    @GetMapping("/mypage")
    public ResponseEntity<Map<String,Object>> getProfile(@RequestBody String eid){
        User user= userRepository.findByUserEid(eid);
        if(user != null){
            return handleSuccess(user);
        }
        return handleError("couldn't find user with "+eid);
    }

    @PutMapping("/mypage")
    @Transactional
    public ResponseEntity<Map<String,Object>> updateProfile(@RequestBody User user){
        User origin = userRepository.findByUserEid(user.getUserEid());
        if(origin != null){
        origin.setUserPassword(user.getUserPassword());
        origin.setUserAddress(user.getUserAddress());
        origin.setUserEmail(user.getUserEmail());
        origin.setUserTelFirst(user.getUserTelFirst());
        origin.setUserTelMiddle(user.getUserTelMiddle());
        origin.setUserTelLast(user.getUserTelLast());
            return handleSuccess(origin);
        }
        return handleError("couldn't find user with "+user.toString());
    }


    private ResponseEntity<Map<String,Object>> handleSuccess(Object data){
        Map<String,Object> result = new HashMap<>();
        result.put("result","success");
        result.put("data",data);
        return new ResponseEntity<Map<String,Object>>(result,HttpStatus.OK);
    }

    private ResponseEntity<Map<String,Object>> handleError(Object data){
        Map<String,Object> result = new HashMap<>();
        result.put("result","failed");
        result.put("data",data);
        return new ResponseEntity<Map<String,Object>>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,Object>> ExceptionHandler(Exception e){
        Map<String,Object> result = new HashMap<>();
        result.put("result","error");
        result.put("data",e.getMessage());
        return new ResponseEntity<Map<String,Object>>(result,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}