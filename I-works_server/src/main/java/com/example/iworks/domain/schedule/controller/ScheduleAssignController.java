package com.example.iworks.domain.schedule.controller;

import com.example.iworks.domain.schedule.dto.scheduleAssign.request.ScheduleAssignSearchParameter;
import com.example.iworks.domain.schedule.dto.scheduleAssign.request.AssigneeInfo;
import com.example.iworks.domain.schedule.dto.scheduleAssign.request.ValidList;
import com.example.iworks.domain.schedule.service.scheduleAssign.ScheduleAssignService;
import com.example.iworks.global.dto.DateCondition;
import com.example.iworks.global.util.Response;
import com.example.iworks.global.util.JwtProvider;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/schedule-assign")
public class ScheduleAssignController {

    private final ScheduleAssignService scheduleAssignService;
    private final Response response;
    private final JwtProvider jwtProvider;

    @PostMapping("/get-by-assignees-and-date")
    public ResponseEntity<?> getAllByAssigneesAndDate(@RequestBody @Validated ScheduleAssignSearchParameter searchParameterAndDate){
        return response.handleSuccess(scheduleAssignService.findByAssignees(searchParameterAndDate.getAssigneeInfos(), searchParameterAndDate.getDateCondition()));
    }
    @GetMapping("/")
    public ResponseEntity<?> getAllByUser(@RequestHeader("Authorization") String authorizationToken){
        int userId = jwtProvider.getUserId(authorizationToken);
        return response.handleSuccess(scheduleAssignService.findByUser(userId, null));
    }
    /** 할일 : 유저의 모든 업무(할일) */
    @PostMapping("/task/date")
    public ResponseEntity<?> getAllByUserAndDate(@RequestHeader("Authorization") String authorizationToken, @RequestBody @Validated DateCondition dateCondition){
        int userId = jwtProvider.getUserId(authorizationToken);
        return response.handleSuccess(scheduleAssignService.findTaskByUser(userId, dateCondition));
    }

}
