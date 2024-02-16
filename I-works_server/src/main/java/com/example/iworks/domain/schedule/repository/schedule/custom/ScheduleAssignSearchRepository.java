package com.example.iworks.domain.schedule.repository.schedule.custom;

import com.example.iworks.domain.schedule.domain.Schedule;
import com.example.iworks.domain.schedule.dto.scheduleAssign.request.AssigneeInfo;
import com.example.iworks.global.dto.DateCondition;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Repository
public interface ScheduleAssignSearchRepository {
   List<Schedule> findScheduleByAssigneeInfo(List<AssigneeInfo> assigneeInfoList, @Validated DateCondition dateCondition, boolean onlyTask);
}
