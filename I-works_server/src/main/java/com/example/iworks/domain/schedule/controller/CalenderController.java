package com.example.iworks.domain.schedule.controller;

import com.example.iworks.domain.schedule.dto.scheduleAssign.request.ScheduleAssignSearchParameterAndDateRequestDto;
import com.example.iworks.domain.schedule.dto.scheduleAssign.request.ScheduleAssignSearchParameterDto;
import com.example.iworks.domain.schedule.service.scheduleAssign.ScheduleAssignService;
import com.example.iworks.global.dto.SearchConditionDate;
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
    public ResponseEntity<?> getAllByUserAndDate(@PathVariable(name = "userId") int userId, @RequestBody SearchConditionDate searchConditionDate){
        return response.handleSuccess(scheduleAssignService.findByUser(userId, searchConditionDate));
    }

}
