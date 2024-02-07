package com.example.iworks.domain.schedule.controller;

import com.example.iworks.domain.schedule.dto.scheduleAssign.request.ScheduleAssignSearchParameter;
import com.example.iworks.domain.schedule.dto.scheduleAssign.request.AssigneeBelong;
import com.example.iworks.domain.schedule.service.scheduleAssign.ScheduleAssignService;
import com.example.iworks.global.dto.DateCondition;
import com.example.iworks.global.model.Response;
import com.example.iworks.global.util.JwtProvider;
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
    private final JwtProvider jwtProvider;

    @PostMapping("/get-by-assignees")
    public ResponseEntity<?> getAllByAssignees(@RequestBody List<AssigneeBelong> searchParameterDto){
        return response.handleSuccess(scheduleAssignService.findByAssignees(searchParameterDto, null));
    }
    @PostMapping("/get-by-assignees-and-date")
    public ResponseEntity<?> getAllByAssigneesAndDate(@RequestBody ScheduleAssignSearchParameter searchParameterAndDate){
        return response.handleSuccess(scheduleAssignService.findByAssignees(searchParameterAndDate.getAssigneeBelongs(), searchParameterAndDate.getDateCondition()));
    }
    @GetMapping("/")
    public ResponseEntity<?> getAllByUser(@RequestHeader("Authorization") String authorizationToken){
        int userId = jwtProvider.getUserId(authorizationToken);
        return response.handleSuccess(scheduleAssignService.findByUser(userId, null));
    }
    /** 할일 : 유저의 모든 업무(할일) */
    @PostMapping("/task/date")
    public ResponseEntity<?> getAllByUserAndDate(@RequestHeader("Authorization") String authorizationToken, @RequestBody DateCondition dateCondition){
        int userId = jwtProvider.getUserId(authorizationToken);
        return response.handleSuccess(scheduleAssignService.findTaskByUser(userId, dateCondition));
    }
}
