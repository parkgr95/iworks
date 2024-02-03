package com.example.iworks.domain.schedule.service.schedule;


import com.example.iworks.domain.schedule.dto.schedule.request.ScheduleCreateRequestDto;
import com.example.iworks.domain.schedule.dto.schedule.response.ScheduleReadOneResponseDto;
import com.example.iworks.domain.schedule.dto.schedule.request.ScheduleUpdateRequestDto;

public interface ScheduleService {
    void createSchedule(ScheduleCreateRequestDto scheduleDto);

    void updateSchedule(int scheduleId, ScheduleUpdateRequestDto scheduleUpdateRequestDto);

    ScheduleReadOneResponseDto readOne(Integer scheduleId);

    void removeSchedule(Integer scheduleId);

    void isFinishedSchedule(int scheduleId, boolean isFinish);

}
