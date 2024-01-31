package com.example.iworks.domain.schedule.repository;

import com.example.iworks.domain.schedule.dto.ScheduleAssignRequestDto;
import com.example.iworks.domain.schedule.dto.ScheduleAssignResponseDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleAssignRepositoryCustom {
   List<ScheduleAssignResponseDto> findScheduleAssignees(List<ScheduleAssignRequestDto> requestDtoList);
}
