package com.example.iworks.domain.schedule.service.schedule;


import com.example.iworks.domain.schedule.domain.Schedule;
import com.example.iworks.domain.schedule.dto.schedule.ScheduleCreateRequestDto;
import com.example.iworks.domain.schedule.dto.schedule.ScheduleReadOneResponseDto;
import com.example.iworks.domain.schedule.dto.schedule.ScheduleUpdateRequestDto;

public interface ScheduleService {
    void createSchedule(ScheduleCreateRequestDto scheduleDto);

    void updateSchedule(int scheduleId, ScheduleUpdateRequestDto scheduleUpdateRequestDto);

    ScheduleReadOneResponseDto readOne(Integer scheduleId);

    void removeSchedule(Integer scheduleId);

    void isFinishedSchedule(int scheduleId, boolean isFinish);

}
