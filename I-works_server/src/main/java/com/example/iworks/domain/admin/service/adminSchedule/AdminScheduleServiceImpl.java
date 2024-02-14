package com.example.iworks.domain.admin.service.adminSchedule;

import com.example.iworks.domain.code.entity.Code;
import com.example.iworks.domain.code.repository.CodeRepository;
import com.example.iworks.domain.meeting.domain.Meeting;
import com.example.iworks.domain.schedule.domain.Schedule;
import com.example.iworks.domain.schedule.dto.schedule.request.ScheduleCreateRequestDto;
import com.example.iworks.domain.schedule.dto.schedule.request.ScheduleUpdateRequestDto;
import com.example.iworks.domain.schedule.dto.schedule.response.ScheduleResponseDto;
import com.example.iworks.domain.schedule.dto.scheduleAssign.request.AssigneeInfo;
import com.example.iworks.domain.schedule.repository.schedule.ScheduleRepository;
import com.example.iworks.domain.user.domain.User;
import com.example.iworks.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminScheduleServiceImpl implements AdminScheduleService {

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

        // Create Schedule
        Schedule schedule = createRequestDto.toScheduleEntity(division, meeting, creator);

        assignUsers(schedule, createRequestDto.getAssigneeInfos());

        Schedule savedSchedule = scheduleRepository.save(schedule);

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

    @Override
    public List<ScheduleResponseDto> getScheduleAll() {
        return scheduleRepository.findAll()
                .stream()
                .map(ScheduleResponseDto::new)
                .collect(toList());
    }

    @Transactional
    @Override
    public void deleteSchedule(Integer scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(IllegalAccessError::new);
        scheduleRepository.delete(schedule);
    }

}
