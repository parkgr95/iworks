package com.example.iworks.domain.schedule.service.schedule;

import com.example.iworks.domain.meeting.domain.Meeting;
import com.example.iworks.domain.schedule.domain.Schedule;
import com.example.iworks.domain.schedule.domain.ScheduleAssign;
import com.example.iworks.domain.schedule.dto.schedule.request.ScheduleCreateRequestDto;
import com.example.iworks.domain.schedule.dto.schedule.response.ScheduleResponseDto;
import com.example.iworks.domain.schedule.dto.schedule.request.ScheduleUpdateRequestDto;
import com.example.iworks.domain.schedule.dto.scheduleAssign.request.AssigneeInfo;
import com.example.iworks.domain.schedule.repository.schedule.ScheduleRepository;
import com.example.iworks.domain.user.domain.User;
import com.example.iworks.domain.user.repository.UserRepository;
import com.example.iworks.domain.code.entity.Code;
import com.example.iworks.domain.code.repository.CodeRepository;
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

    @Transactional
    @Override
    public void createSchedule(int userId, ScheduleCreateRequestDto createRequestDto) {

        // Prepare Relation Entity
        Code division = codeRepository.findById(createRequestDto.getScheduleDivisionCodeId())
                .orElseThrow(() -> new EntityNotFoundException("Schedule Division Code not found"));

        Meeting meeting = createRequestDto.toMeetingEntity();

        User creator = userRepository.findByUserId(userId);

        // Create Schedule Entity
        Schedule schedule = createRequestDto.toScheduleEntity(division, meeting, creator);

        assignUsers(schedule, createRequestDto.getAssigneeInfos());

        scheduleRepository.save(schedule);
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
        scheduleRepository.delete(schedule);
    }

}
