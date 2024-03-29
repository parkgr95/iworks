package com.example.iworks.domain.user.service;

import com.example.iworks.domain.schedule.dto.scheduleAssign.request.AssigneeInfo;
import com.example.iworks.domain.user.domain.User;
import com.example.iworks.domain.user.dto.UserGetMyPageResponseDto;
import com.example.iworks.domain.user.dto.UserUpdateMypageRequestDto;
import com.example.iworks.domain.user.repository.UserRepository;
import com.example.iworks.global.util.JwtProvider;
import com.example.iworks.global.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.iworks.global.common.CodeDef.*;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserServiceImpl implements UserService{
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final Response response;
    private final JwtProvider jwtProvider;

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

    @Transactional
    @Override
    public ResponseEntity<Map<String, Object>> updateUser(String token, UserUpdateMypageRequestDto dto) {
        int id = jwtProvider.getUserId(token);
        User origin = userRepository.findByUserId(id);
        if(origin != null){
            origin.update(dto,bCryptPasswordEncoder);
            return response.handleSuccess(new UserGetMyPageResponseDto(origin));
        }

        return response.handleFail("찾을 수 없는 사용자입니다.",null);
    }

    @Override
    public List<Integer> getUserIdsByAssigneeInfos(List<AssigneeInfo> assigneeInfos) {
        Set<Integer> userIds = new HashSet<>();
        for (AssigneeInfo assigneeInfo : assigneeInfos) {
            if (assigneeInfo.getCategoryCodeId() == TARGET_ALL_CODE_ID) {
                userIds.addAll(userRepository.findAll().stream().map(User::getUserId).collect(Collectors.toSet()));
            }
            if(assigneeInfo.getCategoryCodeId() == TARGET_USER_CODE_ID) {
                userIds.add(assigneeInfo.getAssigneeId());
            }
            if (assigneeInfo.getCategoryCodeId() == TARGET_DEPARTMENT_CODE_ID) {
                userIds.addAll( userRepository.findUsersByDepartmentId(assigneeInfo.getAssigneeId())
                                    .stream().map(User::getUserId).collect(Collectors.toSet()));
            }
            if (assigneeInfo.getCategoryCodeId() == TARGET_TEAM_CODE_ID) {
                userIds.addAll(userRepository.findUsersByTeamId(assigneeInfo.getAssigneeId())
                                    .stream().map(User::getUserId).collect(Collectors.toSet()));
            }
        }
        return new ArrayList<>(userIds);
    }

//    @Override
//    public List<User> getUserListByAssigneeInfos(List<AssigneeInfo> assigneeInfos) {
//        Set<User> userList = new HashSet<>();
//        for (AssigneeInfo assigneeInfo : assigneeInfos) {
//            if (assigneeInfo.getCategoryCodeId() == TARGET_ALL_CODE_ID) {
//                return userRepository.findAll().stream().toList();
//            }
//            if(assigneeInfo.getCategoryCodeId() == TARGET_USER_CODE_ID) {
//                userList.add(userRepository.findById(assigneeInfo.getAssigneeId()).orElse(null));
//            }
//            if (assigneeInfo.getCategoryCodeId() == TARGET_DEPARTMENT_CODE_ID) {
//                userList.addAll(userRepository.findUsersByDepartmentId(assigneeInfo.getAssigneeId()));
//            }
//            if (assigneeInfo.getCategoryCodeId() == TARGET_TEAM_CODE_ID) {
//                userList.addAll(userRepository.findUsersByTeamId(assigneeInfo.getAssigneeId()));
//            }
//        }
//        return new ArrayList<>(userList);
//    }



}
