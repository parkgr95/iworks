package com.example.iworks.domain.schedule.controller;

import com.example.iworks.domain.schedule.dto.scheduleAssign.ScheduleAssignSearchParameterDto;
import com.example.iworks.domain.schedule.service.scheduleAssign.ScheduleAssignService;
import com.example.iworks.global.model.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule-assign")
public class ScheduleAssignController {

    private final ScheduleAssignService scheduleAssignService;
    private final Response response;

    @PostMapping("/get-by-assignees")
    public ResponseEntity<?> getAllScheduleAssignByAssignees(@RequestBody List<ScheduleAssignSearchParameterDto> searchParameterDto){
        return response.handleSuccess(scheduleAssignService.findScheduleAssignBySelectedAssignees(searchParameterDto));
    }
    @GetMapping("/{userId}/assignees")
    public ResponseEntity<?> getAllScheduleAssignByUser(@PathVariable(name = "userId") int userId){
        return response.handleSuccess(scheduleAssignService.findScheduleAssignsByUser(userId));
    }





}
