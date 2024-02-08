package com.example.iworks.domain.notification.dto.notification;

import com.example.iworks.domain.notification.domain.Notification;
import com.example.iworks.global.enumtype.NotificationType;
import com.example.iworks.global.entity.Code;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode
public class NotificationCreateRequestDto {

    private int notificationCategoryCodeId; // 알림 카테고리 아이디

    private int notificationOwnerId; // 알림 주체 아이디

    private String notificationType; // 알림 타입(생성, 삭제, 수정)

    private LocalDateTime notificationCreatedAt; // 알림 생성 일시

    public Notification toEntity(Code categoryCode) {
        return Notification.builder()
                .notificationCategoryCode(categoryCode)
                    .notificationOwnerId(notificationOwnerId)
                    .notificationType(Enum.valueOf(NotificationType.class, notificationType.toUpperCase()))
                    .build();
    }
}
