package com.example.iworks.domain.schedule.service.scheduleAssign;

import com.example.iworks.domain.schedule.dto.scheduleAssign.ScheduleAssignResponseDto;
import com.example.iworks.domain.schedule.dto.scheduleAssign.ScheduleAssignSearchParameterDto;
import com.example.iworks.domain.schedule.repository.scheduleAssign.ScheduleAssignRepository;
import com.example.iworks.domain.team.domain.TeamUser;
import com.example.iworks.domain.team.repository.team.TeamRepository;
import com.example.iworks.domain.team.repository.teamuser.TeamUserRepository;
import com.example.iworks.domain.user.domain.User;
import com.example.iworks.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.example.iworks.global.common.CategoryCodeDef.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleAssignServiceImpl implements ScheduleAssignService{

    @Autowired
    UserRepository userRepository;
    @Autowired
    ScheduleAssignRepository scheduleAssignRepository;
    @Autowired
    TeamUserRepository teamUserRepository;


    /** 유저의 모든 소속에 대하여 할일 배정 및 할일 조회 */
    @Override
    public List<ScheduleAssignResponseDto> findScheduleAssignsByUser(List<ScheduleAssignSearchParameterDto> requestDtoList) {
        return null;
    }

    /** 할일 생성에서 선택된 소속의 할일 배정 및 할일 조회 */
    @Override
    public List<ScheduleAssignResponseDto> findScheduleAssignBySelectedAssignees(List<ScheduleAssignSearchParameterDto> requestDtoList) {
        return null;
    }

    /** 유저의 모든 소속에 대한 할일 배정 검색 조건 조회*/
    @Override
    public List<ScheduleAssignSearchParameterDto> getScheduleAssignSearchParameterDtoByUser(int userId) {

        List<ScheduleAssignSearchParameterDto> searchParameterDtoList = new ArrayList<>();
        User user = userRepository.findById(userId).orElseThrow(IllegalAccessError::new);

        searchParameterDtoList.add(new ScheduleAssignSearchParameterDto(CATEGORY_USER_CODE_ID, userId));
        searchParameterDtoList.add(new ScheduleAssignSearchParameterDto(CATEGORY_DEPARTMENT_CODE_ID, user.getUserDepartment().getDepartmentId()));
        List<TeamUser> teamUsersByUser = teamUserRepository.findTeamUserByUserId(userId);
        for (TeamUser teamUser: teamUsersByUser){
            searchParameterDtoList.add(new ScheduleAssignSearchParameterDto(CATEGORY_TEAM_CODE_ID, teamUser.getTeamUserTeam().getTeamId()));
        }
        return searchParameterDtoList;
    }

    /** 할일 배정 검색 조건에 대한 할일 배정 및 할일 조회 */
    @Override
    public List<ScheduleAssignResponseDto> findScheduleAssignsBySearchParameter(List<ScheduleAssignSearchParameterDto> requestDtoList) {
        return null;
    }


}
