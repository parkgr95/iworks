package com.example.iworks.domain.admin.controller;

import com.example.iworks.domain.schedule.controller.ScheduleController;
import com.example.iworks.domain.schedule.dto.schedule.request.ScheduleCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/admin/schedule")
@RestController
@RequiredArgsConstructor
public class AdminScheduleController {

    private final ScheduleController scheduleController;

    @PostMapping
    public String createSchedule(@RequestHeader("Authorization") String authorizationToken, @RequestBody ScheduleCreateRequestDto scheduleCreateRequestDto){
        return "forward:/api/schedule";
    }
}
