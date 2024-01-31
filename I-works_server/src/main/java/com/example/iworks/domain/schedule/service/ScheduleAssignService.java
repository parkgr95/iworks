package com.example.iworks.domain.schedule.service;

import com.example.iworks.domain.schedule.domain.ScheduleAssign;
import com.example.iworks.domain.schedule.dto.ScheduleAssignRequestDto;
import com.example.iworks.domain.schedule.dto.ScheduleAssignResponseDto;

import java.util.List;

public interface ScheduleAssignService {
    List<ScheduleAssignResponseDto> findScheduleAssignBySelectedAssignees(List<ScheduleAssignRequestDto> requestDtoList);
}
