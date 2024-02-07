package com.example.iworks.domain.notification.service;

import com.example.iworks.domain.notification.dto.notification.NotificationCreateRequestDto;
import com.example.iworks.domain.notification.repository.notification.NotificationRepository;
import com.example.iworks.global.model.entity.Code;
import com.example.iworks.global.model.repository.CodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{

    private final NotificationRepository notificationRepository;
    private final CodeRepository codeRepository;

    @Override
    public void createNotification(NotificationCreateRequestDto notificationDto) {

        Code categoryCode = codeRepository.findById(notificationDto.getNotificationCategoryCodeId())
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리 코드가 없습니다. id=" + notificationDto.getNotificationCategoryCodeId()));

        notificationRepository.save(notificationDto.toEntity(categoryCode));
    }
}
