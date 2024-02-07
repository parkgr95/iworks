package com.example.iworks.domain.user.service;

import com.example.iworks.domain.department.domain.Department;
import com.example.iworks.domain.department.repository.DepartmentRepository;
import com.example.iworks.domain.user.domain.User;
import com.example.iworks.domain.user.dto.UserGetMyPageResponseDto;
import com.example.iworks.domain.user.dto.UserJoinRequestDto;
import com.example.iworks.domain.user.dto.UserUpdateMypageRequestDto;
import com.example.iworks.domain.user.repository.UserRepository;
import com.example.iworks.global.model.Response;
import com.example.iworks.global.model.entity.Code;
import com.example.iworks.global.model.repository.CodeRepository;
import com.example.iworks.global.util.JwtProvider;
import com.example.iworks.global.util.RandomPasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserServiceImpl implements UserService{
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final CodeRepository codeRepository;
    private final Response response;
    private final RandomPasswordUtil randomPasswordUtil;
    private final JwtProvider jwtProvider;
    private static final int ROLE_ADMIN = 6;
    private static final int ROLE_CEO = 7;
    private static final int ROLE_LEADER = 8;
    private static final int ROLE_EMPLOYEE = 9;

    @Transactional
    @Override
    public ResponseEntity<Map<String, Object>> registerUser(UserJoinRequestDto dto) {
        if (userRepository.findByUserEid(dto.getUserEid())!=null){
            return response.handleFail("이미 존재하는 계정입니다.",null);
        }

        if(userRepository.findByUserEmail(dto.getUserEmail())!=null){
            return response.handleFail("이미 존재하는 이메일입니다.",null);
        }

        int deptId = dto.getUserDepartmentId();
        int posCodeId = dto.getUserPositionCodeId();

        User user = new User(dto);
        Department department = departmentRepository.findByDepartmentId(deptId);
        Code code = codeRepository.findCodeByCodeId(posCodeId);
        if(department == null || code == null){
            return response.handleFail("잘못된 부서,직책 값 입력",null);
        }
        user.setDepartment(department);
        user.setPositionCode(code);
        ArrayList<String> roleList = new ArrayList<>();
        if(posCodeId == ROLE_ADMIN){
            roleList.add("ROLE_ADMIN");
        }else if(posCodeId==ROLE_CEO){
            roleList.add("ROLE_CEO");
        }else if(posCodeId==ROLE_LEADER){
            roleList.add("ROLE_LEADER");
        } else if(posCodeId==ROLE_EMPLOYEE){
            roleList.add("ROLE_EMPLOYEE");
        }else{
            return response.handleFail("잘못된 직책 값 입력",null);
        }
        int length = (int) (Math.random() * (12 - 8 + 1)) +8; // 8~12 길이
        String password = randomPasswordUtil.getRandomPassword(length);
        user.setRandomPassword(bCryptPasswordEncoder.encode(password));
        user.setRoleList(roleList);
        userRepository.save(user);
        Map<String, Object> data = new HashMap<>();
        data.put("message","회원가입 성공!");
        data.put("data",password);
        return response.handleSuccess(data);
    }

    @Override
    public ResponseEntity<Map<String, Object>> selectUser(String token) {
        int id = jwtProvider.getUserId(token);
        System.out.println("token id : "+id);
        User user= userRepository.findByUserId(id);
        if(user.getUserEid() != null){
            UserGetMyPageResponseDto dto = new UserGetMyPageResponseDto(user);
            return response.handleSuccess(dto);
        }
        return response.handleFail("찾을 수 없는 사용자입니다.",null);
    }

    @Override
    public ResponseEntity<Map<String, Object>> updateUser(String token, UserUpdateMypageRequestDto dto) {
        int id = jwtProvider.getUserId(token);
        User origin = userRepository.findByUserId(id);
        System.out.println("origin: " + origin);
        if(origin != null){
            origin.update(dto,bCryptPasswordEncoder);
            return response.handleSuccess(new UserGetMyPageResponseDto(origin));
        }

        return response.handleFail("찾을 수 없는 사용자입니다.",null);
    }


}
