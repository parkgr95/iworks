package com.example.iworks.domain.admin.controller;

import com.example.iworks.domain.admin.service.adminSchedule.AdminScheduleService;
import com.example.iworks.domain.schedule.dto.schedule.request.ScheduleCreateRequestDto;
import com.example.iworks.domain.schedule.dto.schedule.request.ScheduleUpdateRequestDto;
import com.example.iworks.global.util.JwtProvider;
import com.example.iworks.global.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/api/admin/schedule")
@RestController
@RequiredArgsConstructor
public class AdminScheduleController {

    private final AdminScheduleService scheduleService;
    private final Response response;
    private final JwtProvider jwtProvider;

    /** 할일 생성 */
    @PostMapping
    public ResponseEntity<?> createSchedule(@RequestHeader("Authorization") String authorizationToken, @RequestBody ScheduleCreateRequestDto scheduleCreateRequestDto){
        int userId = jwtProvider.getUserId(authorizationToken);
        scheduleService.createSchedule(userId, scheduleCreateRequestDto);
        return response.handleSuccess("할일 등록 성공");
    }

    /** 할일 전체 조회 */
    @GetMapping("/")
    public ResponseEntity<Map<String,Object>> getScheduleAll(){
        return response.handleSuccess(scheduleService.getScheduleAll());
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

    /** 할일 삭제 */
    @GetMapping("/{scheduleId}/delete")
    public ResponseEntity<?> deleteSchedule(@PathVariable(name = "scheduleId") int scheduleId){
        scheduleService.deleteSchedule(scheduleId);
        return response.handleSuccess("할일 삭제 성공");
    }
}
