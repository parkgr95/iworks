package com.example.iworks.domain.user.repository;

import com.example.iworks.domain.schedule.dto.scheduleAssign.request.ScheduleAssignSearchParameterDto;
import com.example.iworks.domain.team.repository.team.TeamRepository;
import com.example.iworks.domain.user.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.example.iworks.global.common.CategoryCodeDef.*;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TeamRepository teamRepository;

    /** 유저의 소속정보(유저, 팀, 부서) */
    @Test
    void 유저의_소속정보_불러오기(){
        //given

        int userId = 1;

        //when
        List<ScheduleAssignSearchParameterDto> searchParameterDtoList = new ArrayList<>();
        User user = userRepository.findById(userId).orElseThrow(IllegalAccessError::new);
        searchParameterDtoList.add(new ScheduleAssignSearchParameterDto(CATEGORY_USER_CODE_ID, userId));
        searchParameterDtoList.add(new ScheduleAssignSearchParameterDto(CATEGORY_DEPARTMENT_CODE_ID, user.getUserDepartment().getDepartmentId()));
//        List<TeamUser> userTeamList = teamRepository.
        //then



    }


}