package com.example.iworks.domain.schedule.service;

import com.example.iworks.domain.meeting.domain.Meeting;
import com.example.iworks.domain.meeting.repository.MeetingRepository;
import com.example.iworks.domain.schedule.domain.Schedule;
import com.example.iworks.domain.schedule.domain.ScheduleAssign;
import com.example.iworks.domain.schedule.dto.schedule.ScheduleCreateRequestDto;
import com.example.iworks.domain.schedule.dto.schedule.ScheduleReadOneResponseDto;
import com.example.iworks.domain.schedule.dto.schedule.ScheduleUpdateRequestDto;
import com.example.iworks.domain.schedule.repository.ScheduleRepository;
import com.example.iworks.domain.team.domain.TeamUser;
import com.example.iworks.domain.user.domain.User;
import com.example.iworks.domain.user.repository.UserRepository;
import com.example.iworks.global.model.entity.Code;
import com.example.iworks.global.model.repository.CodeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleServiceImpl implements ScheduleService{

    private final ScheduleRepository scheduleRepository;
    private final CodeRepository codeRepository;
    private final UserRepository userRepository;
    private final MeetingRepository meetingRepository;

    @Override
    public void createSchedule(ScheduleCreateRequestDto scheduleDto) {
        Code scheduleDivision = codeRepository.findById(scheduleDto.getScheduleDivisionCodeId())
                .orElseThrow(() -> new EntityNotFoundException("Schedule Division Code not found"));

        User scheduleCreator = userRepository.findById(scheduleDto.getScheduleCreatorId())
                .orElseThrow(() -> new EntityNotFoundException("Schedule Creator not found"));


        Meeting scheduleMeeting = Meeting.builder()
                .meetingDate(scheduleDto.getMeetingDate())
                .meetingCode("sampleMeetingCode")
                .build();

//        meetingRepository.save(scheduleMeeting);


        Schedule schedule = Schedule.builder()
                .scheduleDivision(scheduleDivision)
                .scheduleTitle(scheduleDto.getScheduleTitle())
                .schedulePriority(scheduleDto.getSchedulePriority())
                .scheduleContent(scheduleDto.getScheduleContent())
                .scheduleStartDate(scheduleDto.getScheduleStartDate())
                .scheduleEndDate(scheduleDto.getScheduleEndDate())
                .schedulePlace(scheduleDto.getSchedulePlace())
                .scheduleMeeting(scheduleMeeting)
                .scheduleCreator(scheduleCreator)
                .scheduleCreatedAt(LocalDateTime.now())
                .build();

        scheduleRepository.save(schedule);
    }

    @Override
    public void updateSchedule(int scheduleId, ScheduleUpdateRequestDto scheduleUpdateRequestDto) {
        int divisionCodeId = scheduleUpdateRequestDto.getScheduleDivisionCodeId();
        Code findCode = codeRepository.findById(divisionCodeId).orElseThrow(IllegalArgumentException::new);
        Schedule findSchedule = scheduleRepository.findById(scheduleId).orElseThrow(IllegalAccessError::new);
        findSchedule.updateSchedule(findCode, scheduleUpdateRequestDto);
    }


    @Override
    public ScheduleReadOneResponseDto readOne(Integer scheduleId) {
        Schedule foundSchedule = scheduleRepository.getReferenceById(scheduleId);
        return new ScheduleReadOneResponseDto(foundSchedule);
    }

    /** 유저의 모든 할일 조회*/
    public List<Schedule> getAllScheduleByUser(User user){
        User userBelong = userRepository.findUserBelong(user.getUserId());
        List<Schedule> scheduleListByUser = new ArrayList<>();

        int userDepartmentId = userBelong.getUserDepartment().getDepartmentId();
        int userId = user.getUserId();
        List<Integer> userTeamIdList = new ArrayList<>();
        for (TeamUser teamUser : userBelong.getUserTeamUserList()){
            userTeamIdList.add(teamUser.getTeamUserTeam().getTeamId());
        }

        //할일 배정


        //할일
        return null;

    }
    @Override
    public void removeSchedule(Integer scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(IllegalAccessError::new);
        scheduleRepository.delete(schedule);
    }
    @Override
    public void isFinishedSchedule(int scheduleId, boolean isFinish) {
        Schedule findSchedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(IllegalAccessError::new);
        findSchedule.isFinished(isFinish);
    }
}
