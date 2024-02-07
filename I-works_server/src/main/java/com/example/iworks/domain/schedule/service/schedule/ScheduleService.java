package com.example.iworks.domain.schedule.service.schedule;


import com.example.iworks.domain.schedule.dto.schedule.request.ScheduleCreateRequestDto;
import com.example.iworks.domain.schedule.dto.schedule.response.ScheduleResponseDto;
import com.example.iworks.domain.schedule.dto.schedule.request.ScheduleUpdateRequestDto;

import java.util.List;

public interface ScheduleService {
    void createSchedule(int userId, ScheduleCreateRequestDto scheduleDto);

    void updateSchedule(int scheduleId, ScheduleUpdateRequestDto scheduleUpdateRequestDto);

    ScheduleResponseDto getSchedule(Integer scheduleId);

    List<ScheduleResponseDto> searchByKeyword(String keyword);

    void deleteSchedule(Integer scheduleId);

    void isFinishedSchedule(int scheduleId, boolean isFinish);

}
