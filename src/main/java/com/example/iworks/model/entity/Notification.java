package com.example.iworks.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
@Getter
@Setter
@IdClass(NotificationId.class)
public class Notification {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_category_code_id",
            referencedColumnName = "code_id",
            nullable = false, updatable = false, insertable = false
    )
    private Code notificationCategoryCode; // 알림 카테고리

    @Id
    @Column(name = "notification_owner_id",
            nullable = false, updatable = false, insertable = false
    )
    private int notificationOwnerId; // 알림 주체 아이디

    @Enumerated(EnumType.STRING)
    @Column(name = "notification_type", nullable = false)
    private NotificationType notificationType; // 알림 타입(생성, 삭제, 수정)

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "notification_created_at", nullable = false)
    private LocalDateTime notificationCreatedAt; // 알림 생성 일시

}
