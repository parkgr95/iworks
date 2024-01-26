package com.example.iworks.domain.schedule.repository;

import com.example.iworks.domain.meeting.domain.Meeting;
import com.example.iworks.domain.schedule.domain.Schedule;
import com.example.iworks.domain.user.domain.User;
import com.example.iworks.global.model.entity.Code;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ScheduleRepositoryTest {

    @Autowired
    ScheduleRepository repository;

    @Test
    public void 할일저장(){
        //given
        Schedule schedule = Schedule.builder()
                .scheduleId(1)
                .scheduleDivisionId(new Code())
                .scheduleTitle("title")
                .schedulePriority('H')
                .scheduleContent("sdfdf")
                .scheduleStartDate(LocalDateTime.now())
                .scheduleEndDate(LocalDateTime.now())
                .scheduleDeadline(LocalDateTime.now())
                .schedulePlace("공원")
                .scheduleMeeting(new Meeting())
                .scheduleCreator(new User())
//                .scheduleCreatedAt(LocalDateTime.now())
                .scheduleModifier(new User())
//                .scheduleModifiedAt(LocalDateTime.now())
                .scheduleAssigns(new ArrayList<>())
                .build();

        //when
        Schedule savedSchedule = repository.save(schedule);
        Schedule findedSchedule = repository.findById(schedule.getScheduleId()).get();
        //then

        Assertions.assertEquals(findedSchedule.getScheduleId(), savedSchedule.getScheduleId());
        Assertions.assertEquals(findedSchedule.getScheduleTitle(), savedSchedule.getScheduleTitle());
        Assertions.assertEquals(findedSchedule.getSchedulePriority(), savedSchedule.getSchedulePriority());

    }

}