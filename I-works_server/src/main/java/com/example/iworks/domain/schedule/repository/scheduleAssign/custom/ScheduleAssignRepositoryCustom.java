package com.example.iworks.domain.schedule.repository.scheduleAssign.custom;

import com.example.iworks.domain.schedule.dto.scheduleAssign.ScheduleAssignSearchParameterDto;
import com.example.iworks.domain.schedule.dto.scheduleAssign.ScheduleAssignFindBySearchParameterResponseDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleAssignRepositoryCustom {
   List<ScheduleAssignFindBySearchParameterResponseDto> findScheduleAssignsBySearchParameter(List<ScheduleAssignSearchParameterDto> requestDtoList);
}
