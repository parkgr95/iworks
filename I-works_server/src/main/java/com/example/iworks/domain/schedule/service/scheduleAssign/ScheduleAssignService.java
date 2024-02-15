package com.example.iworks.domain.schedule.service.scheduleAssign;

import com.example.iworks.domain.schedule.dto.schedule.response.ScheduleResponseDto;
import com.example.iworks.domain.schedule.dto.scheduleAssign.request.AssigneeInfo;
import com.example.iworks.global.dto.DateCondition;

import java.util.List;

public interface ScheduleAssignService {

    /** 캘린더 : 유저의 모든 소속에 대하여 업무배정 조회 */
    List<ScheduleResponseDto> findTaskByUser(int userId, DateCondition dateCondition);

    /** 할일 : 유저의 모든 소속에 대하여 할일 배정 조회 */
    List<ScheduleResponseDto> findByUser(int userId, DateCondition dateCondition);

    /** 할일 생성에서 담당자 할일 배정 조회 */
    List<ScheduleResponseDto> findByAssignees(List<AssigneeInfo> requestDtoList, DateCondition dateCondition);

    /** 유저의 모든 소속에 대한 할일 배정 검색 조건 조회*/
    List<AssigneeInfo> findUserBelongs(int userId);

}
