package com.example.iworks.domain.notification.domain;

import com.example.iworks.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder @NoArgsConstructor @AllArgsConstructor
public class UserNotification {

    @Id @Generated
    private int userNotificationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_notification_user_id", nullable = false)
    private User userNotificationUser; // 유저 아이디 (외래키)

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_notification_notification_id")
    private Notification userNotificationNotification;

    @Builder.Default
    @Column(nullable = false)
    private Boolean userNotificationIsChecked = false; // 알림 확인 여부

    @Builder.Default
    @Column(nullable = false)
    private boolean userNotificationIsDeleted = false; // 알림 삭제 여부

}