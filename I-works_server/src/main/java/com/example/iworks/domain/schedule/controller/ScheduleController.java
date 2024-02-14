package com.example.iworks.domain.schedule.controller;

import com.example.iworks.domain.schedule.dto.schedule.request.ScheduleCreateRequestDto;
import com.example.iworks.domain.schedule.dto.schedule.request.ScheduleUpdateRequestDto;
import com.example.iworks.domain.schedule.service.schedule.ScheduleService;
import com.example.iworks.global.util.Response;
import com.example.iworks.global.util.JwtProvider;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final Response response;
    private final JwtProvider jwtProvider;

    @PostMapping
    public ResponseEntity<?> createSchedule(@RequestHeader("Authorization") String authorizationToken, @Validated @RequestBody ScheduleCreateRequestDto scheduleCreateRequestDto) throws OpenViduJavaClientException, OpenViduHttpException {
        int userId = jwtProvider.getUserId(authorizationToken);
        scheduleService.createSchedule(userId, scheduleCreateRequestDto);
        return response.handleSuccess("할일 등록 성공");
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<Map<String,Object>> getSchedule(@PathVariable(name = "scheduleId") Integer scheduleId){
        return response.handleSuccess(scheduleService.getSchedule(scheduleId));
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchScheduleByKeyword(@RequestParam("keyword") String keyword) {
        return response.handleSuccess(scheduleService.searchByKeyword(keyword));
    }

    @PostMapping("/{scheduleId}/update")
    public ResponseEntity<?> updateSchedule(@PathVariable(name = "scheduleId") int scheduleId, @RequestBody ScheduleUpdateRequestDto scheduleUpdateRequestDto){
        scheduleService.updateSchedule(scheduleId, scheduleUpdateRequestDto);
        return response.handleSuccess("할일 수정 성공");
    }

    @PostMapping("/{scheduleId}/isFinish")
    public ResponseEntity<?> updateIsFinishStatus(@PathVariable(name = "scheduleId") int scheduleId, @RequestBody Boolean isFinish){
        scheduleService.isFinishedSchedule(scheduleId, isFinish);
        return response.handleSuccess("할일 완료 여부 업데이트 성공");
    }

    @GetMapping("/{scheduleId}/delete")
    public ResponseEntity<?> deleteSchedule(@PathVariable(name = "scheduleId") int scheduleId){
        scheduleService.deleteSchedule(scheduleId);
        return response.handleSuccess("할일 삭제 성공");
    }

}
