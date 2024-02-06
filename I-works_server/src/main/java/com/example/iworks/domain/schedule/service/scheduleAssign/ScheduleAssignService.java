package com.example.iworks.domain.schedule.service.scheduleAssign;

import com.example.iworks.domain.schedule.domain.ScheduleAssign;
import com.example.iworks.domain.schedule.dto.scheduleAssign.response.ScheduleAssignResponseDto;
import com.example.iworks.domain.schedule.dto.scheduleAssign.request.AssigneeBelong;
import com.example.iworks.global.dto.DateCondition;

import java.util.List;

public interface ScheduleAssignService {

    /** 캘린더 : 유저의 모든 소속에 대하여 업무배정 조회 */
    List<ScheduleAssignResponseDto> findTaskByUser(int userId, DateCondition dateCondition);

    /** 할일 : 유저의 모든 소속에 대하여 할일 배정 조회 */
    List<ScheduleAssignResponseDto> findByUser(int userId, DateCondition dateCondition);

    /** 할일 생성에서 담당자 할일 배정 조회 */
    List<ScheduleAssignResponseDto> findByAssignees(List<AssigneeBelong> requestDtoList, DateCondition dateCondition);

    /** 유저의 모든 소속에 대한 할일 배정 검색 조건 조회*/
    List<AssigneeBelong> findUserBelongs(int userId);

    /** 할일 배정 검색 조건에 대한 할일 배정 및 할일 조회 */
    List<ScheduleAssign> findScheduleAssignsBySearchParameter(List<AssigneeBelong> assigneeBelongList, DateCondition dateCondition, boolean onlyTask);

}
