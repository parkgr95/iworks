package com.example.iworks.domain.schedule.service.scheduleAssign;

import com.example.iworks.domain.schedule.dto.scheduleAssign.ScheduleAssignSearchParameterDto;
import com.example.iworks.domain.schedule.dto.scheduleAssign.ScheduleAssignResponseDto;

import java.util.List;

public interface ScheduleAssignService {

    /** 유저의 모든 소속에 대하여 할일 배정 및 할일 조회 */
    List<ScheduleAssignResponseDto> findScheduleAssignsByUser(List<ScheduleAssignSearchParameterDto> requestDtoList);

    /** 할일 생성에서 선택된 소속의 할일 배정 및 할일 조회 */
    List<ScheduleAssignResponseDto> findScheduleAssignBySelectedAssignees(List<ScheduleAssignSearchParameterDto> requestDtoList);

    /** 유저의 모든 소속에 대한 할일 배정 검색 조건 조회*/
    List<ScheduleAssignSearchParameterDto> getScheduleAssignSearchParameterDtoByUser(int userId);

    /** 할일 배정 검색 조건에 대한 할일 배정 및 할일 조회 */
    List<ScheduleAssignResponseDto> findScheduleAssignsBySearchParameter(List<ScheduleAssignSearchParameterDto> requestDtoList);

}
