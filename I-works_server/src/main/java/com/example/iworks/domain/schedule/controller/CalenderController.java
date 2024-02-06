package com.example.iworks.domain.schedule.controller;

import com.example.iworks.domain.schedule.service.scheduleAssign.ScheduleAssignService;
import com.example.iworks.global.dto.DateCondition;
import com.example.iworks.global.model.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/calender")
public class CalenderController {

    private final ScheduleAssignService scheduleAssignService;
    private final Response response;

    /** 캘린더 : 유저의 모든 할일*/
    @GetMapping("/{userId}/date")
    public ResponseEntity<?> getAllByUserAndDate(@PathVariable(name = "userId") int userId, @RequestBody DateCondition dateCondition){
        return response.handleSuccess(scheduleAssignService.findByUser(userId, dateCondition));
    }

}
