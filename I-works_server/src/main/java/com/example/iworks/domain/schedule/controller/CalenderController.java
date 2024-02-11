package com.example.iworks.domain.schedule.controller;

import com.example.iworks.domain.schedule.service.scheduleAssign.ScheduleAssignService;
import com.example.iworks.global.dto.DateCondition;
import com.example.iworks.global.util.Response;
import com.example.iworks.global.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/calender")
public class CalenderController {

    private final ScheduleAssignService scheduleAssignService;
    private final Response response;
    private final JwtProvider jwtProvider;

    /** 캘린더 : 유저의 모든 할일*/
    @GetMapping("/date")
    public ResponseEntity<?> getAllByUserAndDate(@RequestHeader("Authorization") String authorizationToken, @RequestBody DateCondition dateCondition){
        int userId = jwtProvider.getUserId(authorizationToken);
        return response.handleSuccess(scheduleAssignService.findByUser(userId, dateCondition));
    }

}
