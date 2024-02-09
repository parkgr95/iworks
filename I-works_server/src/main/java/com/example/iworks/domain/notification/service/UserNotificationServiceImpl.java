package com.example.iworks.domain.notification.service;

import com.example.iworks.domain.board.domain.Board;
import com.example.iworks.domain.board.repository.BoardRepository;
import com.example.iworks.domain.meeting.domain.Meeting;
import com.example.iworks.domain.meeting.repository.MeetingRepository;
import com.example.iworks.domain.notification.domain.UserNotification;
import com.example.iworks.domain.notification.dto.usernotification.request.UserNotificationCreateRequestDto;
import com.example.iworks.domain.notification.dto.usernotification.response.UserNotificationGetAllByUserResponseDto;
import com.example.iworks.domain.notification.repository.usernotification.UserNotificationRepository;
import com.example.iworks.domain.schedule.domain.Schedule;
import com.example.iworks.domain.schedule.repository.schedule.ScheduleRepository;
import com.example.iworks.domain.user.domain.User;
import com.example.iworks.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserNotificationServiceImpl implements UserNotificationService {

    private final UserNotificationRepository userNotificationRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final ScheduleRepository scheduleRepository;
    private final MeetingRepository meetingRepository;

    @Transactional
    @Override
    public void create(UserNotificationCreateRequestDto createRequestDto) {
        User receiver = userRepository.findById(createRequestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id=" + createRequestDto.getUserId()));

        Board board = null;
        Schedule schedule = null;
        Meeting meeting = null;

        if (createRequestDto.getBoardId() != 0) {
            board = boardRepository.findById(createRequestDto.getBoardId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + createRequestDto.getBoardId()));
        } else if (createRequestDto.getScheduleId() != 0) {
            schedule = scheduleRepository.findById(createRequestDto.getScheduleId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 일정이 없습니다. id=" + createRequestDto.getScheduleId()));
        } else if (createRequestDto.getMeetingId() != 0) {
            meeting = meetingRepository.findById(createRequestDto.getMeetingId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 회의가 없습니다. id=" + createRequestDto.getMeetingId()));
        }
        userNotificationRepository.save(createRequestDto.toEntity(receiver, board, schedule, meeting));

    }

    @Transactional
    @Override
    public void delete(int userNotificationId) {
        UserNotification foundUserNotification = userNotificationRepository.findById(userNotificationId)
                .orElseThrow(() -> new IllegalArgumentException("해당 알림이 없습니다. id=" + userNotificationId));
        foundUserNotification.delete();
    }

    @Override
    public List<UserNotificationGetAllByUserResponseDto> getAllByUserId(int userId) {
        return userNotificationRepository.findByUserId(userId)
                .stream()
                .map(UserNotificationGetAllByUserResponseDto::new)
                .toList();
    }

    @Override
    public List<UserNotificationGetAllByUserResponseDto> getAllAboutBoardByUserId(int userId) {
        return userNotificationRepository.findCategoryBoardByUserId(userId)
                .stream()
                .map(UserNotificationGetAllByUserResponseDto::new)
                .toList();
    }

    @Override
    public List<UserNotificationGetAllByUserResponseDto> getAllAboutScheduleByUserId(int userId) {
        return null;
    }

    @Override
    public List<UserNotificationGetAllByUserResponseDto> getAllAboutMeetingByUserId(int userId) {
        return null;
    }
}
