package com.example.iworks.domain.user.controller;

import com.example.iworks.domain.department.domain.Department;
import com.example.iworks.domain.user.domain.User;
import com.example.iworks.domain.user.repository.UserRepository;
import com.example.iworks.global.model.Response;
import com.example.iworks.global.model.entity.Code;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

@RequestMapping("/api/user")
@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final Response response;
    @Value("${jwt.secret}")
    String SECRET_KEY;

    @PostMapping("/join")
    public ResponseEntity<Map<String, Object>> join(@RequestBody User user) {
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
        return response.handleSuccess("join success");
    }

    @GetMapping("/mypage")
    public ResponseEntity<Map<String,Object>> getProfile(@RequestHeader("Authorization") String token){
        Jws<Claims> jws = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token.replace("Bearer ",""));
        String eid = jws.getBody().getSubject();
        System.out.println("token eid : "+eid);
        User user= userRepository.findByUserEid(eid);
        if(user != null){
            return response.handleSuccess(user);
        }
        return response.handleError("couldn't find user with "+eid);
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

        return response.handleError("couldn't find user with "+user.toString());
    }

}