package com.example.iworks.domain.notification.controller;


import com.example.iworks.domain.notification.dto.usernotification.request.UserNotificationCreateRequestDto;
import com.example.iworks.domain.notification.service.UserNotificationService;
import com.example.iworks.global.model.Response;
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

}
