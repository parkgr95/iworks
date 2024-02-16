package com.example.iworks.domain.notification.controller;


import com.example.iworks.domain.notification.dto.usernotification.request.UserNotificationCreateRequestDto;
import com.example.iworks.domain.notification.service.UserNotificationService;
import com.example.iworks.global.util.JwtProvider;
import com.example.iworks.global.util.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/notification")
public class UserNotificationController {
    
    private final UserNotificationService userNotificationService;
    private final Response response;
    private final JwtProvider jwtProvider;
    private final long timeOutInMilliSec = 100000L;

    /**
     * @deprecated 컨트롤러에서 알림 생성 로직을 처리하지 않는다.
     */
    @Deprecated
    @PostMapping
    public ResponseEntity<Map<String, Object>> createNotification(@RequestBody UserNotificationCreateRequestDto userNotificationCreateRequestDto) {
        userNotificationService.create(userNotificationCreateRequestDto);
        return response.handleSuccess("알림 생성 완료");
    }

    @GetMapping("/ask-message")
    public DeferredResult<String> test(@RequestHeader("Authorization") String token) {
        int userId = jwtProvider.getUserId(token);

        String timeOutResp = "Time Out.";

        DeferredResult<String> deferredResult = new DeferredResult<>(timeOutInMilliSec, timeOutResp);
        CompletableFuture.runAsync(()->{
            try {
                //Long polling task
                while (userNotificationService.getCountIsNotSent(userId) == 0){
                    log.info("Waiting for message...");
                    TimeUnit.SECONDS.sleep(3);
                }

                String message = userNotificationService.getOneMessage(userId);
                log.info("Message received : {}  ", message);
                deferredResult.setResult(message);

            }catch (Exception ex){
                deferredResult.setErrorResult(ex);
            }
        });
        return deferredResult;
    }

    @PostMapping("/{userNotificationId}/delete")
    public ResponseEntity<Map<String, Object>> deleteNotification(@PathVariable("userNotificationId") int notificationId) {
        userNotificationService.delete(notificationId);
        return response.handleSuccess("알림 삭제 완료");
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllUserNotificationsByUserId(@RequestHeader("Authorization") String token){
        int userId = jwtProvider.getUserId(token);
        return response.handleSuccess(userNotificationService.getAllByUserId(userId));
    }

    @GetMapping("/board")
    public ResponseEntity<Map<String, Object>> getAllAboutBoardByUserId(@RequestHeader("Authorization") String token){
        int userId = jwtProvider.getUserId(token);
        return response.handleSuccess(userNotificationService.getAllAboutBoardByUserId(userId));
    }

    @GetMapping("/schedule")
    public ResponseEntity<Map<String, Object>> getAllAboutScheduleByUserId(@RequestHeader("Authorization") String token){
        int userId = jwtProvider.getUserId(token);
        return response.handleSuccess(userNotificationService.getAllAboutScheduleByUserId(userId));
    }

    @GetMapping("/meeting")
    public ResponseEntity<Map<String, Object>> getAllAboutMeetingByUserId(@RequestHeader("Authorization") String token){
        int userId = jwtProvider.getUserId(token);
        return response.handleSuccess(userNotificationService.getAllAboutMeetingByUserId(userId));
    }

}
