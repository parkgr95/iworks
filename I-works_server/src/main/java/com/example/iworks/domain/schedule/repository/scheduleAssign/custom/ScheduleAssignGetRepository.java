package com.example.iworks.domain.schedule.repository.scheduleAssign.custom;

import com.example.iworks.domain.schedule.domain.ScheduleAssign;
import com.example.iworks.domain.schedule.dto.scheduleAssign.request.AssigneeBelong;
import com.example.iworks.global.dto.DateCondition;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleAssignGetRepository {
   List<ScheduleAssign> findScheduleAssignsBySearchParameter(List<AssigneeBelong> assigneeBelongList, DateCondition dateCondition, boolean onlyTask);
}
