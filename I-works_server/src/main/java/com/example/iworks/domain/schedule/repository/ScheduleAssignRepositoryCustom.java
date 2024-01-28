package com.example.iworks.domain.schedule.repository;

import com.example.iworks.domain.schedule.dto.ScheduleAssignRequestDto;
import com.example.iworks.domain.schedule.dto.ScheduleAssignResponseDto;

import java.util.List;

public interface ScheduleAssignRepositoryCustom {
   List<ScheduleAssignResponseDto> findScheduleAssignees(List<ScheduleAssignRequestDto> requestDtoList);
}
