package com.example.iworks.domain.schedule.repository.scheduleAssign.custom;

import com.example.iworks.domain.schedule.domain.ScheduleAssign;
import com.example.iworks.domain.schedule.dto.scheduleAssign.request.AssigneeInfo;
import com.example.iworks.global.dto.DateCondition;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleAssignGetRepository {
   List<ScheduleAssign> findScheduleAssignsBySearchParameter(List<AssigneeInfo> assigneeInfoList, DateCondition dateCondition, boolean onlyTask);
}
