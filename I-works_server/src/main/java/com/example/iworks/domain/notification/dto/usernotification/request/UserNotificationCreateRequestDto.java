package com.example.iworks.domain.notification.dto.usernotification.request;

import com.example.iworks.domain.board.domain.Board;
import com.example.iworks.domain.meeting.domain.Meeting;
import com.example.iworks.domain.notification.domain.Notification;
import com.example.iworks.domain.notification.domain.UserNotification;
import com.example.iworks.domain.schedule.domain.Schedule;
import com.example.iworks.domain.user.domain.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode
@Builder @AllArgsConstructor
@NoArgsConstructor
public class UserNotificationCreateRequestDto {

    private int userId; // 유저 아이디

    private int boardId; // 게시판 아이디

    private int scheduleId; // 할일 아이디

    private int meetingId; // 회의 아이디

    private String notificationContent; // 알림 내용

    private String notificationType; // 알림 타입(생성, 삭제, 수정)

    public UserNotification toEntity(User user, Board board, Schedule schedule, Meeting meeting) {
        return UserNotification.builder()
                .userNotificationReceiver(user)
                .userNotificationBoard(board)
                .userNotificationSchedule(schedule)
                .userNotificationMeeting(meeting)
                .userNotificationContent(notificationContent)
                .build();
    }
}
