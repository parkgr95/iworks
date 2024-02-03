package com.example.iworks.domain.schedule.service.scheduleAssign;

import com.example.iworks.domain.schedule.dto.scheduleAssign.response.ScheduleAssignResponseDto;
import com.example.iworks.domain.schedule.dto.scheduleAssign.request.ScheduleAssignSearchParameterDto;
import com.example.iworks.global.dto.SearchConditionDate;

import java.util.List;

public interface ScheduleAssignService {

    /** 유저의 모든 소속에 대하여 할일 배정 및 할일 조회 */
    List<ScheduleAssignResponseDto> findByUser(int userId, SearchConditionDate searchConditionDate);

    /** 할일 생성에서 선택된 소속의 할일 배정 및 할일 조회 */
    List<ScheduleAssignResponseDto> findByAssignees(List<ScheduleAssignSearchParameterDto> requestDtoList, SearchConditionDate searchConditionDate);

    /** 유저의 모든 소속에 대한 할일 배정 검색 조건 조회*/
    List<ScheduleAssignSearchParameterDto> getScheduleAssignSearchParameterDtoByUser(int userId);

    /** 할일 배정 검색 조건에 대한 할일 배정 및 할일 조회 */
    List<ScheduleAssignResponseDto> findScheduleAssignsBySearchParameter(List<ScheduleAssignSearchParameterDto> requestDtoList);
    List<ScheduleAssignResponseDto> findScheduleAssignsBySearchParameter(List<ScheduleAssignSearchParameterDto> requestDtoList, SearchConditionDate searchConditionDate);


}
