package com.example.iworks.domain.schedule.repository.schedule.custom;

import com.example.iworks.domain.schedule.dto.schedule.response.ScheduleResponseDto;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ScheduleSearchRepository {
   List<ScheduleResponseDto> findByKeyword(String keyword);
}
