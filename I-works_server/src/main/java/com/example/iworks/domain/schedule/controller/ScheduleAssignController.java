package com.example.iworks.domain.schedule.controller;

import com.example.iworks.domain.schedule.dto.scheduleAssign.request.ScheduleAssignSearchParameterAndDateRequestDto;
import com.example.iworks.domain.schedule.dto.scheduleAssign.request.ScheduleAssignSearchParameterDto;
import com.example.iworks.domain.schedule.service.scheduleAssign.ScheduleAssignService;
import com.example.iworks.global.dto.SearchConditionDate;
import com.example.iworks.global.model.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedule-assign")
public class ScheduleAssignController {

    private final ScheduleAssignService scheduleAssignService;
    private final Response response;

    @PostMapping("/get-by-assignees")
    public ResponseEntity<?> getAllByAssignees(@RequestBody List<ScheduleAssignSearchParameterDto> searchParameterDto){
        return response.handleSuccess(scheduleAssignService.findByAssignees(searchParameterDto, null));
    }
    @PostMapping("/get-by-assignees-and-date")
    public ResponseEntity<?> getAllByAssigneesAndDate(@RequestBody ScheduleAssignSearchParameterAndDateRequestDto searchParameterAndDate){
        return response.handleSuccess(scheduleAssignService.findByAssignees(searchParameterAndDate.getSearchParameterDto(), searchParameterAndDate.getSearchConditionDate()));
    }
    @GetMapping("/{userId}")
    public ResponseEntity<?> getAllByUser(@PathVariable(name = "userId") int userId){
        return response.handleSuccess(scheduleAssignService.findByUser(userId, null));
    }

    /** 할일 : 유저의 모든 업무(할일) */
    @GetMapping("/{userId}/task/date")
    public ResponseEntity<?> getAllByUserAndDate(@PathVariable(name = "userId") int userId, @RequestBody SearchConditionDate searchConditionDate){
        return response.handleSuccess(scheduleAssignService.findTaskByUser(userId, searchConditionDate));
    }
}
