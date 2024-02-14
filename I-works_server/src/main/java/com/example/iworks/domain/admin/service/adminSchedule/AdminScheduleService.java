package com.example.iworks.domain.admin.service.adminSchedule;

import com.example.iworks.domain.schedule.dto.schedule.request.ScheduleCreateRequestDto;
import com.example.iworks.domain.schedule.dto.schedule.request.ScheduleUpdateRequestDto;
import com.example.iworks.domain.schedule.dto.schedule.response.ScheduleResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface AdminScheduleService {
    void createSchedule(int userId, ScheduleCreateRequestDto scheduleDto);

    void updateSchedule(int scheduleId, ScheduleUpdateRequestDto scheduleUpdateRequestDto);

    List<ScheduleResponseDto> getScheduleAll();

    ScheduleResponseDto getSchedule(Integer scheduleId);

    List<ScheduleResponseDto> searchByKeyword(String keyword);

    void deleteSchedule(Integer scheduleId);
}
