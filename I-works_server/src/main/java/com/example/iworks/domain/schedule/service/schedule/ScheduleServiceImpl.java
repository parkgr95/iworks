package com.example.iworks.domain.schedule.service.schedule;

import com.example.iworks.domain.code.entity.Code;
import com.example.iworks.domain.code.repository.CodeRepository;
import com.example.iworks.domain.meeting.domain.Meeting;
import com.example.iworks.domain.notification.dto.usernotification.request.UserNotificationCreateRequestDto;
import com.example.iworks.domain.notification.service.UserNotificationService;
import com.example.iworks.domain.schedule.domain.Schedule;
import com.example.iworks.domain.schedule.dto.schedule.request.ScheduleCreateRequestDto;
import com.example.iworks.domain.schedule.dto.schedule.request.ScheduleUpdateRequestDto;
import com.example.iworks.domain.schedule.dto.schedule.response.ScheduleResponseDto;
import com.example.iworks.domain.schedule.dto.scheduleAssign.request.AssigneeInfo;
import com.example.iworks.domain.schedule.repository.schedule.ScheduleRepository;
import com.example.iworks.domain.user.domain.User;
import com.example.iworks.domain.user.repository.UserRepository;
import com.example.iworks.domain.user.service.UserService;
import com.example.iworks.global.enumtype.NotificationType;
import com.example.iworks.global.util.OpenViduUtil;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public void createSchedule(int userId, ScheduleCreateRequestDto createRequestDto) throws OpenViduJavaClientException, OpenViduHttpException {

        // Prepare Relation Entity
        Code division = codeRepository.findById(createRequestDto.getScheduleDivisionCodeId())
                .orElseThrow(() -> new EntityNotFoundException("Schedule Division Code not found"));

        User creator = userRepository.findByUserId(userId);

        Meeting meeting = null;

        if (createRequestDto.getIsCreateMeeting()){
            String sessionId = openViduUtil.createSessionId();
            meeting = createRequestDto.toMeetingEntity(sessionId);
        }

        // Create Schedule
        Schedule schedule = createRequestDto.toScheduleEntity(division, meeting, creator);

        // Assign Users
        assignUsers(schedule, createRequestDto.getAssigneeInfos());

        Schedule savedSchedule = scheduleRepository.save(schedule);

        // Create Assignees Notification
        createAssigneesNotification(createRequestDto.getAssigneeInfos(), savedSchedule);

    }

//    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected void createAssigneesNotification(List<AssigneeInfo> assigneeInfos, Schedule savedSchedule) {
        try {

            //Find all user by assigneeInfos
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

    private void assignUsers(Schedule schedule, List<AssigneeInfo> assigneeInfos){
        for (AssigneeInfo assigneeInfo : assigneeInfos) {

            Code belongDivision = codeRepository.findById(assigneeInfo.getCategoryCodeId())
                    .orElseThrow(() -> new EntityNotFoundException("Assignee Category not found"));

            schedule.addScheduleAssigns(assigneeInfo.toScheduleAssignEntity(belongDivision));
        }
    }

    @Override
    public ScheduleResponseDto getSchedule(Integer scheduleId) {
        Schedule foundSchedule = scheduleRepository.getReferenceById(scheduleId);
        return new ScheduleResponseDto(foundSchedule);
    }

    @Override
    public List<ScheduleResponseDto> searchByKeyword(String keyword) {
        return scheduleRepository.findByKeyword(keyword);
    }

    @Transactional
    @Override
    public void updateSchedule(int scheduleId, ScheduleUpdateRequestDto scheduleUpdateRequestDto) {
        int divisionCodeId = scheduleUpdateRequestDto.getScheduleDivisionCodeId();
        Code findCode = codeRepository.findById(divisionCodeId).orElseThrow(IllegalArgumentException::new);
        Schedule findSchedule = scheduleRepository.findById(scheduleId).orElseThrow(IllegalAccessError::new);
        findSchedule.updateSchedule(findCode, scheduleUpdateRequestDto);
    }


    @Transactional
    @Override
    public void isFinishedSchedule(int scheduleId, boolean isFinish) {
        Schedule findSchedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(IllegalAccessError::new);
        findSchedule.isFinished(isFinish);
    }

    @Transactional
    @Override
    public void deleteSchedule(Integer scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(IllegalAccessError::new);
        schedule.delete();
//        scheduleRepository.delete(schedule);
    }

}
