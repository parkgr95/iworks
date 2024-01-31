package com.example.iworks.domain.schedule.service;


import com.example.iworks.domain.schedule.domain.Schedule;

public interface ScheduleService {
    void registerSchedule(Schedule schedule);

    void updateSchedule(Schedule schedule);

    Schedule getSchedule(Integer scheduleId);

    void removeSchedule(Integer scheduleId);
}
