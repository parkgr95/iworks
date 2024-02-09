package com.example.iworks.domain.notification.controller;


import com.example.iworks.domain.notification.dto.usernotification.request.UserNotificationCreateRequestDto;
import com.example.iworks.domain.notification.service.UserNotificationService;
import com.example.iworks.global.model.Response;
import com.example.iworks.global.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user-notification")
public class UserNotificationController {
    
    private final UserNotificationService userNotificationService;
    private final Response response;
    private final JwtProvider jwtProvider;

    @PostMapping
    public ResponseEntity<Map<String, Object>> createNotification(@RequestBody UserNotificationCreateRequestDto userNotificationCreateRequestDto) {
        userNotificationService.createUserNotification(userNotificationCreateRequestDto);
        return response.handleSuccess("알림 생성 완료");
    }

    @GetMapping("/{userNotificationId}/delete")
    public ResponseEntity<Map<String, Object>> deleteNotification(@PathVariable("userNotificationId") int notificationId) {
        userNotificationService.deleteUserNotification(notificationId);
        return response.handleSuccess("알림 삭제 완료");
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllUserNotificationsByUserId(@RequestHeader("Authorization") String token){
        int userId = jwtProvider.getUserId(token);
        return response.handleSuccess(userNotificationService.getAllUserNotificationsByUserId(userId));
    }

}
