package com.example.iworks.domain.schedule.service.schedule;

import com.example.iworks.domain.code.entity.Code;
import com.example.iworks.domain.code.exception.CodeException;
import com.example.iworks.domain.code.repository.CodeRepository;
import com.example.iworks.domain.notification.dto.usernotification.request.UserNotificationCreateRequestDto;
import com.example.iworks.domain.notification.service.UserNotificationService;
import com.example.iworks.domain.schedule.domain.Schedule;
import com.example.iworks.domain.schedule.domain.ScheduleAssign;
import com.example.iworks.domain.schedule.dto.schedule.request.ScheduleCreateRequestDto;
import com.example.iworks.domain.schedule.dto.schedule.request.ScheduleUpdateRequestDto;
import com.example.iworks.domain.schedule.dto.schedule.response.ScheduleResponseDto;
import com.example.iworks.domain.schedule.dto.scheduleAssign.request.AssigneeInfo;
import com.example.iworks.domain.schedule.exception.ScheduleErrorCode;
import com.example.iworks.domain.schedule.exception.ScheduleException;
import com.example.iworks.domain.schedule.repository.schedule.ScheduleRepository;
import com.example.iworks.domain.user.domain.User;
import com.example.iworks.domain.user.exception.UserException;
import com.example.iworks.domain.user.repository.UserRepository;
import com.example.iworks.domain.user.service.UserService;
import com.example.iworks.global.enumtype.NotificationType;
import com.example.iworks.global.util.OpenViduUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.example.iworks.domain.code.exception.CodeErrorCode.CODE_NOT_EXIST;
import static com.example.iworks.domain.schedule.exception.ScheduleErrorCode.SCHEDULE_NOT_EXIST;
import static com.example.iworks.domain.user.exception.UserErrorCode.USER_NOT_EXIST;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final CodeRepository codeRepository;
    private final UserRepository userRepository;
    private final UserNotificationService userNotificationService;
    private final UserService userService;
    private final OpenViduUtil openViduUtil;

    @Transactional
    @Override
    public void createSchedule(int userId, ScheduleCreateRequestDto createRequestDto) {

        Code divisionCode = findCode(createRequestDto.getScheduleDivisionCodeId());
        User creator = findUser(userId);
        Schedule schedule = createRequestDto.toScheduleEntity(divisionCode, openViduUtil.createSessionId(), creator);
        schedule.addScheduleAssignList(toScheduleAssignList(createRequestDto.getAssigneeInfos()));
        Schedule savedSchedule = scheduleRepository.save(schedule);

        createAssigneesNotification(createRequestDto.getAssigneeInfos(), savedSchedule);
    }

    @Override
    public ScheduleResponseDto getSchedule(Integer scheduleId) {
        Schedule findSchedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleException(ScheduleErrorCode.SCHEDULE_NOT_EXIST));
        return new ScheduleResponseDto(findSchedule);
    }

    @Override
    public List<ScheduleResponseDto> searchByKeyword(String keyword) {
        return scheduleRepository.findByKeyword(keyword);
    }

    @Transactional
    @Override
    public void updateSchedule(int scheduleId, ScheduleUpdateRequestDto scheduleUpdateRequestDto) {
        Code divisionCode = findCode(scheduleUpdateRequestDto.getScheduleDivisionCodeId());
        Schedule findSchedule = findSchedule(scheduleId);
        findSchedule.updateSchedule(divisionCode, scheduleUpdateRequestDto);
    }

    @Transactional
    @Override
    public void isFinishedSchedule(int scheduleId, boolean isFinish) {
        Schedule findSchedule = findSchedule(scheduleId);
        findSchedule.isFinished(isFinish);
    }

    @Transactional
    @Override
    public void deleteSchedule(Integer scheduleId) {
        Schedule schedule = findSchedule(scheduleId);
        schedule.delete();
    }

    private List<ScheduleAssign> toScheduleAssignList(List<AssigneeInfo> assigneeInfos) {
        List<ScheduleAssign> scheduleAssigns = new ArrayList<>();
        for (AssigneeInfo assigneeInfo : assigneeInfos) {
            scheduleAssigns.add(ScheduleAssign.builder()
                    .scheduleAssigneeCategory(findCode(assigneeInfo.getCategoryCodeId()))
                    .scheduleAssigneeId(assigneeInfo.getAssigneeId()).build());
        }
        return scheduleAssigns;
    }
    private void createAssigneesNotification(List<AssigneeInfo> assigneeInfos, Schedule savedSchedule) {
        try {
            List<Integer> userIds = userService.getUserIdsByAssigneeInfos(assigneeInfos);
            for ( int userId : userIds) {
                UserNotificationCreateRequestDto notificationCreateRequestDto = UserNotificationCreateRequestDto.builder()
                        .scheduleId(savedSchedule.getScheduleId())
                        .userId(userId)
                        .notificationContent("sample : 새로운 스케쥴이 생성되었습니다! ")
                        .notificationType(NotificationType.CREATE.toString())
                        .build();
                userNotificationService.create(notificationCreateRequestDto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Schedule findSchedule(int scheduleId) {
        return scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleException(SCHEDULE_NOT_EXIST));
    }

    private User findUser(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserException(USER_NOT_EXIST));
    }

    private Code findCode(int codeId) {
        return codeRepository.findById(codeId)
                .orElseThrow(() -> new CodeException(CODE_NOT_EXIST));
    }

}
