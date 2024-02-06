package com.example.iworks.domain.schedule.dto.scheduleAssign.request;

import com.example.iworks.global.dto.DateCondition;
import lombok.*;

import java.util.List;

@Getter
public class ScheduleAssignSearchParameter {
    private List<AssigneeBelong> assigneeBelongs;
    private DateCondition dateCondition;
}
