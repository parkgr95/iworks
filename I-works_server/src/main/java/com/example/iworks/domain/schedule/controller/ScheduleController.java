package com.example.iworks.domain.schedule.controller;

import com.example.iworks.domain.schedule.dto.schedule.request.ScheduleCreateRequestDto;
import com.example.iworks.domain.schedule.dto.schedule.request.ScheduleUpdateRequestDto;
import com.example.iworks.domain.schedule.service.schedule.ScheduleService;
import com.example.iworks.global.model.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final Response response;

    @PostMapping("/create")
    public ResponseEntity<?> createSchedule(@RequestBody ScheduleCreateRequestDto schedule){
        scheduleService.createSchedule(schedule);
        return response.handleSuccess("할일 등록 성공");
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<?> getSchedule(@PathVariable(name = "scheduleId") Integer scheduleId){
        return response.handleSuccess(scheduleService.readOne(scheduleId));
    }

    @PostMapping("/{scheduleId}/update")
    public ResponseEntity<?> updateSchedule(@PathVariable(name = "scheduleId") int scheduleId, @RequestBody ScheduleUpdateRequestDto scheduleUpdateRequestDto){
        scheduleService.updateSchedule(scheduleId, scheduleUpdateRequestDto);
        return response.handleSuccess("할일 수정 성공");
    }

    @PostMapping("/{scheduleId}/isFinish")
    public ResponseEntity<?> updateIsFinishStatus(@PathVariable(name = "scheduleId") int scheduleId, @RequestBody boolean isFinish){
        scheduleService.isFinishedSchedule(scheduleId, isFinish);
        return response.handleSuccess("할일 완료 여부 업데이트 성공");
    }

    @PostMapping("/{scheduleId}/delete")
    public ResponseEntity<?> deleteSchedule(@PathVariable(name = "scheduleId") int scheduleId){
        scheduleService.removeSchedule(scheduleId);
        return response.handleSuccess("할일 삭제 성공");
    }


}
