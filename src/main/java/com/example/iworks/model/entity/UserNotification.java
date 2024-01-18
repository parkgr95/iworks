package com.example.iworks.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_notification")
@Getter
@Setter
@IdClass(UserNotificationId.class)
public class UserNotification {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 유저 아이디 (외래키)

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "notification_category_code_id", referencedColumnName = "notification_category_code_id"),
            @JoinColumn(name = "notification_owner_id", referencedColumnName = "notification_owner_id" )
    })
    private Notification notification;

    @Column(name = "is_checked", nullable = false)
    private boolean isChecked; // 알림 확인 여부

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted; // 알림 삭제 여부

}
