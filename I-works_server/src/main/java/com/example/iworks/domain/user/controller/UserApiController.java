package com.example.iworks.domain.user.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.iworks.domain.department.domain.Department;
import com.example.iworks.domain.user.domain.User;
import com.example.iworks.domain.user.repository.UserRepository;
import com.example.iworks.global.config.util.RandomPasswordUtil;
import com.example.iworks.global.model.Response;
import com.example.iworks.global.model.entity.Code;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
    private final Response response;
    private final RandomPasswordUtil randomPasswordUtil;
    @Value("${jwt.secret}")
    String SECRET_KEY;

    @PostMapping("/join")
    public ResponseEntity<Map<String, Object>> join(@RequestBody User user) {
        if (userRepository.findByUserEid(user.getUserEid())!=null){
            return response.handleFail("이미 존재하는 계정입니다.");
        }

        Department dept = new Department();
        dept.setDepartmentId(1);
        dept.setDepartmentName("testDept");
        Code code = new Code();
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
        int length = (int) (Math.random() * (12 - 8 + 1)) +8; // 8~12 길이
        String password = randomPasswordUtil.getRandomPassword(length);
        user.setUserPassword(bCryptPasswordEncoder.encode(password));
        user.setRoleList(roleList);
        userRepository.save(user);
        Map<String, Object> map = new HashMap<>();
        map.put("message","회원가입 성공!");
        map.put("data",password);
        return response.handleSuccess(map);
    }

    @GetMapping("/mypage")
    public ResponseEntity<Map<String,Object>> getProfile(@RequestHeader("Authorization") String token){
        token = token.replace("Bearer ","");
        String eid = JWT.require(Algorithm.HMAC512(SECRET_KEY)).build().verify(token).getClaim("eid").asString();
        System.out.println("token eid : "+eid);
        User user= userRepository.findByUserEid(eid);
        if(user != null){
            return response.handleSuccess(user);
        }
        return response.handleFail("couldn't find user with "+eid);
    }

    @PutMapping("/mypage")
    @Transactional
    public ResponseEntity<Map<String,Object>> updateProfile(@RequestBody User user){
        User origin = userRepository.findByUserEid(user.getUserEid());
        System.out.print("origin:");
        System.out.println(origin);
        if(origin != null){

            if(user.getUserPassword() != null){
                origin.setUserPassword(bCryptPasswordEncoder.encode(user.getUserPassword()));
            }

            if(user.getUserAddress()!=null){
                origin.setUserAddress(user.getUserAddress());
            }

            if(user.getUserEmail()!=null){
                origin.setUserEmail(user.getUserEmail());
            }

            if(user.getUserTel() !=null){
                origin.setUserTel(user.getUserTel());
            }
            return response.handleSuccess(origin);
        }

        return response.handleFail("couldn't find user with "+user.toString());
    }

}