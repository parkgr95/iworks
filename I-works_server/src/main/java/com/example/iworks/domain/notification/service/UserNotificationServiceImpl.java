package com.example.iworks.domain.notification.service;

import com.example.iworks.domain.board.entity.Board;
import com.example.iworks.domain.board.exception.BoardException;
import com.example.iworks.domain.board.repository.BoardRepository;
import com.example.iworks.domain.meeting.domain.Meeting;
import com.example.iworks.domain.meeting.exception.MeetingException;
import com.example.iworks.domain.meeting.repository.MeetingRepository;
import com.example.iworks.domain.notification.domain.UserNotification;
import com.example.iworks.domain.notification.dto.usernotification.request.UserNotificationCreateRequestDto;
import com.example.iworks.domain.notification.dto.usernotification.response.UserNotificationGetAllByUserResponseDto;
import com.example.iworks.domain.notification.exception.NotificationException;
import com.example.iworks.domain.notification.repository.usernotification.UserNotificationRepository;
import com.example.iworks.domain.schedule.domain.Schedule;
import com.example.iworks.domain.schedule.exception.ScheduleException;
import com.example.iworks.domain.schedule.repository.schedule.ScheduleRepository;
import com.example.iworks.domain.user.domain.User;
import com.example.iworks.domain.user.exception.UserException;
import com.example.iworks.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.iworks.domain.board.exception.BoardErrorCode.BOARD_NOT_EXIST;
import static com.example.iworks.domain.meeting.exception.MeetingErrorCode.MEETING_NOT_EXIST;
import static com.example.iworks.domain.notification.exception.NotificationErrorCode.NOTIFICATION_NOT_EXIST;
import static com.example.iworks.domain.notification.exception.NotificationErrorCode.USER_NOTIFICATION_NOT_EXIST;
import static com.example.iworks.domain.schedule.exception.ScheduleErrorCode.SCHEDULE_NOT_EXIST;
import static com.example.iworks.domain.user.exception.UserErrorCode.USER_NOT_EXIST;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserNotificationServiceImpl implements UserNotificationService {

    private final UserNotificationRepository userNotificationRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final ScheduleRepository scheduleRepository;
    private final MeetingRepository meetingRepository;

//    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    @Transactional
    public void create(UserNotificationCreateRequestDto createRequestDto) {

        User receiver = findUser(createRequestDto.getUserId());
        Board board = null;
        Schedule schedule = null;
        Meeting meeting = null;

        if (createRequestDto.getBoardId() != 0) {
            board = findBoard(createRequestDto.getBoardId());
        } else if (createRequestDto.getScheduleId() != 0) {
            schedule = findSchedule(createRequestDto.getScheduleId());
        } else if (createRequestDto.getMeetingId() != 0) {
            meeting = findMeeting(createRequestDto.getMeetingId());
        }
        userNotificationRepository.save(createRequestDto.toEntity(receiver, board, schedule, meeting));
    }

    @Transactional
    @Override
    public void delete(int userNotificationId) {
        findNotification(userNotificationId).delete();
    }

    @Override
    public List<UserNotificationGetAllByUserResponseDto> getAllByUserId(int userId) {
        return userNotificationRepository.findAllByUserId(userId)
                .stream()
                .map(UserNotificationGetAllByUserResponseDto::new)
                .toList();
    }

    @Override
    public List<UserNotificationGetAllByUserResponseDto> getAllAboutBoardByUserId(int userId) {
        return userNotificationRepository.findAllCategoryBoardByUserId(userId)
                .stream()
                .map(UserNotificationGetAllByUserResponseDto::new)
                .toList();
    }

    @Override
    public List<UserNotificationGetAllByUserResponseDto> getAllAboutScheduleByUserId(int userId) {
        return userNotificationRepository.findAllCategoryScheduleByUserId(userId)
                .stream()
                .map(UserNotificationGetAllByUserResponseDto::new)
                .toList();
    }

    @Override
    public List<UserNotificationGetAllByUserResponseDto> getAllAboutMeetingByUserId(int userId) {
        return userNotificationRepository.findAllCategoryMeetingByUserId(userId)
                .stream()
                .map(UserNotificationGetAllByUserResponseDto::new)
                .toList();
    }

    @Override
    public long getCountIsNotSent(int userId) {
        return userNotificationRepository.countOfIsNotSent(userId);
    }

    @Override
    public String getOneMessage(int userId) throws NotificationException{
        List<UserNotification> userNotificationList = userNotificationRepository.findAllIsNotSentByUserId(userId);//영속성 컨텍스트에 있음
        if (userNotificationList.isEmpty()) {
            throw new NotificationException(USER_NOTIFICATION_NOT_EXIST);
        }
        String message = userNotificationList.get(0).getUserNotificationContent();
        userNotificationList.get(0).setIsSent();
        return message;
    }

    private User findUser(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserException(USER_NOT_EXIST));
    }

    private UserNotification findNotification(int userNotificationId) {
        return userNotificationRepository.findById(userNotificationId)
                .orElseThrow(() -> new NotificationException(NOTIFICATION_NOT_EXIST));
    }

    private Schedule findSchedule(int scheduleId) {
        return scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleException(SCHEDULE_NOT_EXIST));
    }
    private Board findBoard(int boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardException(BOARD_NOT_EXIST));
    }

    private Meeting findMeeting(int meetingId) {
        return meetingRepository.findById(meetingId)
                .orElseThrow(() -> new MeetingException(MEETING_NOT_EXIST));
    }
}
