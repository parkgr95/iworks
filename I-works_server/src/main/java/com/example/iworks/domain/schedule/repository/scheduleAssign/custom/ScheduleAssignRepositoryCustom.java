package com.example.iworks.domain.schedule.repository.scheduleAssign.custom;

import com.example.iworks.domain.schedule.dto.scheduleAssign.request.ScheduleAssignSearchParameterDto;
import com.example.iworks.domain.schedule.dto.scheduleAssign.response.ScheduleAssignResponseDto;
import com.example.iworks.global.dto.SearchConditionDate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleAssignRepositoryCustom {
   List<ScheduleAssignResponseDto> findScheduleAssignsBySearchParameter(List<ScheduleAssignSearchParameterDto> requestDtoList);
   List<ScheduleAssignResponseDto> findScheduleAssignsBySearchParameter(List<ScheduleAssignSearchParameterDto> requestDtoList, SearchConditionDate searchConditionDate, boolean onlyTask);
}
