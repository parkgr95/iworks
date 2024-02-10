package com.example.iworks.domain.schedule.service.schedule;

import com.example.iworks.domain.meeting.domain.Meeting;
import com.example.iworks.domain.meeting.repository.MeetingRepository;
import com.example.iworks.domain.schedule.domain.Schedule;
import com.example.iworks.domain.schedule.domain.ScheduleAssign;
import com.example.iworks.domain.schedule.dto.schedule.request.ScheduleCreateRequestDto;
import com.example.iworks.domain.schedule.dto.schedule.response.ScheduleResponseDto;
import com.example.iworks.domain.schedule.dto.schedule.request.ScheduleUpdateRequestDto;
import com.example.iworks.domain.schedule.dto.scheduleAssign.request.AssigneeBelong;
import com.example.iworks.domain.schedule.repository.schedule.ScheduleRepository;
import com.example.iworks.domain.schedule.repository.scheduleAssign.ScheduleAssignRepository;
import com.example.iworks.domain.user.domain.User;
import com.example.iworks.domain.user.repository.UserRepository;
import com.example.iworks.domain.code.entity.Code;
import com.example.iworks.domain.code.repository.CodeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final CodeRepository codeRepository;
    private final UserRepository userRepository;
    private final MeetingRepository meetingRepository;
    private final ScheduleAssignRepository scheduleAssignRepository;

    @Override
    public void createSchedule(int userId, ScheduleCreateRequestDto createRequestDto) {
        System.out.println("userId = " + userId);
        Code scheduleDivision = codeRepository.findById(createRequestDto.getScheduleDivisionCodeId())
                .orElseThrow(() -> new EntityNotFoundException("Schedule Division Code not found"));

        User creator = userRepository.findByUserId(userId);

        Meeting scheduleMeeting = Meeting.builder()
                .meetingDate(createRequestDto.getMeetingDate())
                .meetingCode("sampleMeetingCode")
                .build();

        Schedule schedule = Schedule.builder()
                .scheduleDivision(scheduleDivision)
                .scheduleTitle(createRequestDto.getScheduleTitle())
                .schedulePriority(createRequestDto.getSchedulePriority())
                .scheduleContent(createRequestDto.getScheduleContent())
                .scheduleStartDate(createRequestDto.getScheduleStartDate())
                .scheduleEndDate(createRequestDto.getScheduleEndDate())
                .schedulePlace(createRequestDto.getSchedulePlace())
                .scheduleMeeting(scheduleMeeting)
                .scheduleCreator(creator)
                .scheduleCreatedAt(LocalDateTime.now())
                .build();

        /** need to refactoring */
        for (AssigneeBelong assigneeBelong : createRequestDto.getAssigneeBelongs()) {

            Code belongCategory = codeRepository.findById(assigneeBelong.getCategoryCodeId())
                    .orElseThrow(() -> new EntityNotFoundException("Assignee Category not found"));

            schedule.addScheduleAssigns(ScheduleAssign.builder()
                            .scheduleAssigneeCategory(belongCategory)
                    .scheduleAssigneeId(assigneeBelong.getAssigneeId()).build());
        }
        scheduleRepository.save(schedule);
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

    @Override
    public void updateSchedule(int scheduleId, ScheduleUpdateRequestDto scheduleUpdateRequestDto) {
        int divisionCodeId = scheduleUpdateRequestDto.getScheduleDivisionCodeId();
        Code findCode = codeRepository.findById(divisionCodeId).orElseThrow(IllegalArgumentException::new);
        Schedule findSchedule = scheduleRepository.findById(scheduleId).orElseThrow(IllegalAccessError::new);
        findSchedule.updateSchedule(findCode, scheduleUpdateRequestDto);
    }

    /** 할일 완료 여부 */
    @Override
    public void isFinishedSchedule(int scheduleId, boolean isFinish) {
        Schedule findSchedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(IllegalAccessError::new);
        findSchedule.isFinished(isFinish);
    }

    @Override
    public void deleteSchedule(Integer scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(IllegalAccessError::new);
        scheduleRepository.delete(schedule);
    }


}
