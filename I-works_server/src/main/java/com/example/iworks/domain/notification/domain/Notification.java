package com.example.iworks.domain.notification.domain;

import com.example.iworks.global.enumtype.NotificationType;
import com.example.iworks.global.model.entity.Code;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "notification")
@Getter
@Builder @AllArgsConstructor @NoArgsConstructor
public class Notification {

    @Id @Generated
    @Column(name = "notification_id" )
    private Integer notificationId; //알림 아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_category_code_id", nullable = false)
    private Code notificationCategoryCode; // 알림 카테고리

    @Column(name = "notification_owner_id", nullable = false)
    private Integer notificationOwnerId; // 알림 주체 아이디

    @Enumerated(EnumType.STRING)
    @Column(name = "notification_type", nullable = false)
    private NotificationType notificationType; // 알림 타입(생성, 삭제, 수정)

    @Builder.Default
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "notification_created_at", nullable = false)
    private LocalDateTime notificationCreatedAt = LocalDateTime.now(); // 알림 생성 일시

    @Builder.Default
    @OneToMany(mappedBy = "userNotificationNotification")
    private List<UserNotification> notificationUserNotifications = new ArrayList<>();

}