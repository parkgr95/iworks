package com.example.iworks.domain.schedule.dto.scheduleAssign.request;

import com.example.iworks.global.dto.DateCondition;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Getter
public class ScheduleAssignSearchParameter {

    @NotNull
    private List<@Valid AssigneeInfo> assigneeInfos;

    @NotNull
    private @Valid DateCondition dateCondition;
}
