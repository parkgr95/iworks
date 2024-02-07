package com.example.iworks.domain.notification.controller;


import com.example.iworks.domain.notification.dto.notification.NotificationCreateRequestDto;
import com.example.iworks.domain.notification.service.NotificationService;
import com.example.iworks.global.model.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/notification")
public class NotificationController {
    
    private final NotificationService notificationService;
    private final Response response;

    @PostMapping("/")
    public ResponseEntity<Map<String, Object>> createNotification(@RequestBody NotificationCreateRequestDto notificationCreateRequestDto) {
        notificationService.createNotification(notificationCreateRequestDto);
        return response.handleSuccess("알림 생성 완료");
    }

}
