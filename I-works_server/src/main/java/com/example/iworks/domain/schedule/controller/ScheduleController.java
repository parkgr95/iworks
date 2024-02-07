package com.example.iworks.domain.schedule.controller;

import com.example.iworks.domain.schedule.dto.schedule.request.ScheduleCreateRequestDto;
import com.example.iworks.domain.schedule.dto.schedule.request.ScheduleUpdateRequestDto;
import com.example.iworks.domain.schedule.service.schedule.ScheduleService;
import com.example.iworks.global.model.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final Response response;

//    @ResponseStatus(HttpStatus.OK)
//    public String createSchedule(@RequestBody ScheduleCreateRequestDto scheduleCreateRequestDto){
    /** 할일 생성 */
    @PostMapping("/")
    public ResponseEntity<?> createSchedule(@RequestHeader("Authorization") String authorizationHeader, @RequestBody ScheduleCreateRequestDto scheduleCreateRequestDto){
        String jwtToken = authorizationHeader.substring("Bearer ".length());
        scheduleService.createSchedule(jwtToken, scheduleCreateRequestDto);
        return response.handleSuccess("할일 등록 성공");
    }

    /** 할일 상세 조회 */
    @GetMapping("/{scheduleId}")
    public ResponseEntity<Map<String,Object>> getSchedule(@PathVariable(name = "scheduleId") Integer scheduleId){
        return response.handleSuccess(scheduleService.getSchedule(scheduleId));
    }
    /** 할일 검색 */
    @GetMapping("/search")
    public ResponseEntity<?> searchScheduleByKeyword(@RequestParam("keyword") String keyword) {
        return response.handleSuccess(scheduleService.searchByKeyword(keyword));
    }
    /** 할일 업데이트 */
    @PostMapping("/{scheduleId}/update")
    public ResponseEntity<?> updateSchedule(@PathVariable(name = "scheduleId") int scheduleId, @RequestBody ScheduleUpdateRequestDto scheduleUpdateRequestDto){
        scheduleService.updateSchedule(scheduleId, scheduleUpdateRequestDto);
        return response.handleSuccess("할일 수정 성공");
    }

    /** 할일 완료 여부 업데이트 */
    @PostMapping("/{scheduleId}/isFinish")
    public ResponseEntity<?> updateIsFinishStatus(@PathVariable(name = "scheduleId") int scheduleId, @RequestBody boolean isFinish){
        scheduleService.isFinishedSchedule(scheduleId, isFinish);
        return response.handleSuccess("할일 완료 여부 업데이트 성공");
    }

    /** 할일 삭제 */
    @PostMapping("/{scheduleId}/delete")
    public ResponseEntity<?> deleteSchedule(@PathVariable(name = "scheduleId") int scheduleId){
        scheduleService.removeSchedule(scheduleId);
        return response.handleSuccess("할일 삭제 성공");
    }


}
