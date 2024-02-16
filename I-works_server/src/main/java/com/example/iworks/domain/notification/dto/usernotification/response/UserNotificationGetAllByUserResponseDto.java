package com.example.iworks.domain.notification.dto.usernotification.response;

import com.example.iworks.domain.notification.domain.UserNotification;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

//@NoArgsConstructor
@Getter
public class UserNotificationGetAllByUserResponseDto {

    private final Integer userNotificationId; //알림 아이디

    private final String userNotificationContent; // 알림 내용

    private final LocalDateTime userNotificationCreatedAt; // 알림 생성 일시

    private final Boolean userNotificationIsChecked; // 알림 확인 여부

    public UserNotificationGetAllByUserResponseDto(UserNotification userNotification){
        this.userNotificationId = userNotification.getUserNotificationId();
        this.userNotificationContent = userNotification.getUserNotificationContent();
        this.userNotificationCreatedAt = userNotification.getUserNotificationCreatedAt();
        this.userNotificationIsChecked = userNotification.getUserNotificationIsChecked();
    }
}
