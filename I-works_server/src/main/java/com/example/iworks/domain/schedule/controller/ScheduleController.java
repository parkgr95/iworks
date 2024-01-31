package com.example.iworks.domain.schedule.controller;

import com.example.iworks.domain.schedule.domain.Schedule;
import com.example.iworks.domain.schedule.service.ScheduleService;
import com.example.iworks.global.model.Response;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final Response response;

    @PostMapping("/create")
    public ResponseEntity<?> createSchedule(@RequestBody Schedule schedule){
        scheduleService.registerSchedule(schedule);
        return response.handleSuccess("할일 등록 완료");
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<?> getSchedule(@PathVariable int scheduleId){
        return response.handleSuccess(scheduleService.getSchedule(scheduleId));
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateSchedule(@RequestBody Schedule schedule){
        scheduleService.updateSchedule(schedule);
        return response.handleSuccess("할일 수정 완료");
    }

    @PostMapping("/{scheduleId}")
    public ResponseEntity<?> updateSchedule(@PathVariable int scheduleId){
        scheduleService.removeSchedule(scheduleId);
        return response.handleSuccess("할일 삭제 완료");
    }

}
