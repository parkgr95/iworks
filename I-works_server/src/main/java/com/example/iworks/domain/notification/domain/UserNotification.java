package com.example.iworks.domain.notification.domain;

import com.example.iworks.domain.board.entity.Board;
import com.example.iworks.domain.meeting.domain.Meeting;
import com.example.iworks.domain.schedule.domain.Schedule;
import com.example.iworks.domain.user.domain.User;
import com.example.iworks.global.enumtype.NotificationType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder @NoArgsConstructor @AllArgsConstructor
@Table(name = "user_notification")
public class UserNotification {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userNotificationId;

    @Enumerated(EnumType.STRING)
    @Column(name = "notification_type")
    private NotificationType notificationType; // 알림 타입(생성, 삭제, 수정)

    @Column(name = "user_notification_content", nullable = false)
    private String userNotificationContent; // 알림 내용

    @Builder.Default
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "notification_created_at", nullable = false)
    private LocalDateTime userNotificationCreatedAt = LocalDateTime.now(); // 알림 생성 일시

    @Builder.Default
    @Column(name = "user_notification_is_checked", nullable = false)
    private Boolean userNotificationIsChecked = false; // 알림 확인 여부

    @Builder.Default
    @Column(name = "user_notification_is_deleted", nullable = false)
    private Boolean userNotificationIsDeleted = false; // 알림 삭제 여부

    @Builder.Default
    @Column(name = "user_notification_is_sent", nullable = false)
    private Boolean userNotificationIsSent = false; // 알림 전송 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_notification_receiver_id", nullable = false)
    private User userNotificationReceiver; // 받는 유저 아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_notification_board_id", nullable = true)
    private Board userNotificationBoard; // 게시글 아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_notification_schedule_id", nullable = true)
    private Schedule userNotificationSchedule; // 일정 아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_notification_meeting_id", nullable = true)
    private Meeting userNotificationMeeting; // 회의 아이디

    public void delete() {
        this.userNotificationIsDeleted = true;
    }
    public void setIsSent() {
        this.userNotificationIsSent = true;
    }

}