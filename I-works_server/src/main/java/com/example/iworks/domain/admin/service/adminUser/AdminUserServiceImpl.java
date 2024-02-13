package com.example.iworks.domain.admin.service.adminUser;

import com.example.iworks.domain.admin.dto.adminUser.request.AdminUserCreateRequestDto;
import com.example.iworks.domain.admin.dto.adminUser.request.AdminUserUpdateRequestDto;
import com.example.iworks.domain.admin.dto.adminUser.response.AdminUserResponseDto;
import com.example.iworks.domain.code.entity.Code;
import com.example.iworks.domain.code.repository.CodeRepository;
import com.example.iworks.domain.department.domain.Department;
import com.example.iworks.domain.department.repository.DepartmentRepository;
import com.example.iworks.domain.user.domain.User;
import com.example.iworks.domain.user.dto.UserGetMyPageResponseDto;
import com.example.iworks.domain.user.repository.UserRepository;
import com.example.iworks.global.util.JwtProvider;
import com.example.iworks.global.util.RandomPasswordUtil;
import com.example.iworks.global.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AdminUserServiceImpl implements AdminUserService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final CodeRepository codeRepository;
    private final Response response;
    private final RandomPasswordUtil randomPasswordUtil;
    private final JwtProvider jwtProvider;

    @Transactional
    @Override
    public ResponseEntity<Map<String, Object>> registerUser(AdminUserCreateRequestDto dto) {
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
        if(department == null ){
            return response.handleFail("잘못된 부서 입력",null);
        }

        if(code == null ){
            return response.handleFail("잘못된 직책 입력",null);
        }
        user.setDepartment(department);
        user.setPositionCode(code);
        ArrayList<String> roleList = new ArrayList<>();

        roleList.add(code.getCodeName());

        int length = (int) (Math.random() * (12 - 8 + 1)) +8; // 8~12 길이
        String password = randomPasswordUtil.getRandomPassword(length);
        user.setRandomPassword(bCryptPasswordEncoder.encode(password));
        user.setRoleList(roleList);
        userRepository.save(user);
        return response.handleSuccess(password);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getUserAll() {
        Stream<AdminUserResponseDto> result = userRepository.findAll().stream()
                .map(AdminUserResponseDto::new);
        return response.handleSuccess(result);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getUser(String token) {
        int id = jwtProvider.getUserId(token);
        System.out.println("token id : "+id);
        User user= userRepository.findByUserId(id);
        if(user.getUserEid() != null){
            AdminUserResponseDto dto = new AdminUserResponseDto(user);
            return response.handleSuccess(dto);
        }
        return response.handleFail("찾을 수 없는 사용자입니다.",null);
    }

    @Override
    public ResponseEntity<Map<String, Object>> updateUser(String token, AdminUserUpdateRequestDto dto) {
        int id = jwtProvider.getUserId(token);
        User origin = userRepository.findByUserId(id);
        System.out.println("origin: " + origin);
        if(origin != null){
            origin.update(dto,bCryptPasswordEncoder);
            return response.handleSuccess(new AdminUserResponseDto(origin));
        }

        return response.handleFail("찾을 수 없는 사용자입니다.",null);
    }

    @Override
    public ResponseEntity<Map<String, Object>> deleteUser(String token) {
        int id = jwtProvider.getUserId(token);
        User user = userRepository.findByUserId(id);

        if(user != null){
            user.delete();
            return response.handleSuccess(new AdminUserResponseDto(user));
        }

        return response.handleFail("찾을 수 없는 사용자입니다.",null);
    }

}
