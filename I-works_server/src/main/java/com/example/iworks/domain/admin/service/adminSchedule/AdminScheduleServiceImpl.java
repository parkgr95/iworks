package com.example.iworks.domain.admin.service.adminSchedule;

import com.example.iworks.domain.admin.dto.adminSchedule.AdminScheduleResponseDto;
import com.example.iworks.domain.department.domain.Department;
import com.example.iworks.domain.department.repository.DepartmentRepository;
import com.example.iworks.domain.schedule.domain.Schedule;
import com.example.iworks.domain.schedule.domain.ScheduleAssign;
import com.example.iworks.domain.schedule.repository.schedule.ScheduleRepository;
import com.example.iworks.domain.team.domain.Team;
import com.example.iworks.domain.team.repository.team.TeamRepository;
import com.example.iworks.domain.user.domain.User;
import com.example.iworks.domain.user.exception.UserException;
import com.example.iworks.domain.user.repository.UserRepository;
import io.netty.handler.codec.DecoderException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.example.iworks.domain.user.exception.UserErrorCode.USER_NOT_EXIST;
import static com.example.iworks.global.common.CodeDef.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminScheduleServiceImpl implements AdminScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final TeamRepository teamRepository;

    @Override
    public List<AdminScheduleResponseDto> getScheduleAll() {

//        List<AdminScheduleResponseDto> scheduleResponseDtoList = scheduleRepository.findAll()
//                .stream()
//                .map(AdminScheduleResponseDto::new)
//                .collect(toList());
//
        List<AdminScheduleResponseDto> scheduleResponseDtoList = new ArrayList<>();

        List<Schedule> scheduleList = scheduleRepository.findAll();
        for (Schedule schedule : scheduleList){
            AdminScheduleResponseDto adminScheduleResponseDto = new AdminScheduleResponseDto(schedule);
            adminScheduleResponseDto.assigneeList = new ArrayList<>();
            for (ScheduleAssign scheduleAssign : schedule.getScheduleAssigns()){
                int assigneeDivision = scheduleAssign.getScheduleAssigneeCategory().getCodeId();
                if (assigneeDivision == TARGET_USER_CODE_ID){
                    adminScheduleResponseDto.assigneeList.add(findUser(scheduleAssign.getScheduleAssigneeId()).getUserName());
                }
                if(assigneeDivision == TARGET_DEPARTMENT_CODE_ID){
                    adminScheduleResponseDto.assigneeList.add(findDepartment(scheduleAssign.getScheduleAssigneeId()).getDepartmentName());
                }
                if(assigneeDivision == TARGET_TEAM_CODE_ID){
                    adminScheduleResponseDto.assigneeList.add(findTeam(scheduleAssign.getScheduleAssigneeId()).getTeamName());
                }
            }
            scheduleResponseDtoList.add(adminScheduleResponseDto);
        }
        return scheduleResponseDtoList;
    }

    private User findUser(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserException(USER_NOT_EXIST));
    }
    private Department findDepartment(int departmentId) {
        return departmentRepository.findById(departmentId)
                .orElseThrow(() -> new DecoderException("Department not exist"));
    }
    private Team findTeam(int teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(() -> new DecoderException("Team not exist"));
    }
}
