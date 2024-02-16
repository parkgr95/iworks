package com.example.iworks.domain.admin.service.adminUser;

import com.example.iworks.domain.admin.dto.adminUser.request.AdminUserCreateRequestDto;
import com.example.iworks.domain.admin.dto.adminUser.request.AdminUserUpdateRequestDto;
import com.example.iworks.domain.admin.dto.adminUser.response.AdminUserResponseDto;
import com.example.iworks.domain.code.entity.Code;
import com.example.iworks.domain.code.repository.CodeRepository;
import com.example.iworks.domain.department.domain.Department;
import com.example.iworks.domain.department.repository.DepartmentRepository;
import com.example.iworks.domain.user.domain.User;
import com.example.iworks.domain.user.repository.UserRepository;
import com.example.iworks.global.util.EmailSender;
import com.example.iworks.global.util.RandomStringUtil;
import com.example.iworks.global.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Stream;

import static com.example.iworks.global.common.CodeDef.*;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AdminUserServiceImpl implements AdminUserService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final CodeRepository codeRepository;
    private final Response response;
    private final RandomStringUtil randomStringUtil;
    private final EmailSender emailSender;

    @Transactional
    @Override
    public ResponseEntity<Map<String, Object>> registerUser(AdminUserCreateRequestDto dto) {
        if (userRepository.findByUserEid(dto.getUserEid())!=null){
            return response.handleFail("이미 존재하는 계정입니다.",null);
        }

        if(userRepository.getAvailableUserByEmail(dto.getUserEmail())!=null){
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
        String role =null;
        if(code.getCodeId() == POSITION_EMPLOYEE_CODE_ID){
            role = "ROLE_EMPLOYEE";
        }else if(code.getCodeId() == POSITION_LEADER_CODE_ID){
            role = "ROLE_LEADER";
        }else if(code.getCodeId() == POSITION_CEO_CODE_ID){
            role = "ROLE_CEO";
        }else if(code.getCodeId() == POSITION_ADMIN_CODE_ID){
            role = "ROLE_ADMIN";
        }else{
            return response.handleFail("잘못된 직책 입력",null);
        }
        roleList.add(role);

        int length = (int) (Math.random() * (12 - 8 + 1)) +8; // 8~12 길이
        String password = randomStringUtil.getRandomPassword(length);
        user.setRandomPassword(bCryptPasswordEncoder.encode(password));
        user.setRoleList(roleList);

        StringBuilder sb = new StringBuilder();
        sb.append("입사를 축하합니다.").append("\n");
        sb.append("사번 : ").append(user.getUserEid()).append("\n");
        sb.append("임시 비밀번호 : ").append(password).append("\n");
        String message = sb.toString();

        try {
            emailSender.sendEmail(user.getUserEmail(), "iworks 메시지", message);
        }catch (InterruptedException e){
            return response.handleFail("email 전송 실패",null);
        }

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
    public ResponseEntity<Map<String, Object>> getUser(int userId) {
        System.out.println("token id : "+userId);
        User user= userRepository.findByUserId(userId);
        if(user.getUserEid() != null){
            AdminUserResponseDto dto = new AdminUserResponseDto(user);
            return response.handleSuccess(dto);
        }
        return response.handleFail("찾을 수 없는 사용자입니다.",null);
    }

    @Transactional
    @Override
    public ResponseEntity<Map<String, Object>> updateUser(int userId, AdminUserUpdateRequestDto dto) {
        User origin = userRepository.findByUserId(userId);
        System.out.println("origin: " + origin);
        if(origin != null){
            origin.update(dto);
            return response.handleSuccess("회원 정보 수정 완료");
        }

        return response.handleFail("찾을 수 없는 사용자입니다.",null);
    }

    @Transactional
    @Override
    public ResponseEntity<Map<String, Object>> deleteUser(int userId) {
        User user = userRepository.findByUserId(userId);

        if(user != null){
            user.delete();
            return response.handleSuccess("회원 탈퇴 완료");
        }

        return response.handleFail("찾을 수 없는 사용자입니다.",null);
    }

}
