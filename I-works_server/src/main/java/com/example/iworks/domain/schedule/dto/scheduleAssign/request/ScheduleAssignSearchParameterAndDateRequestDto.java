package com.example.iworks.domain.schedule.dto.scheduleAssign.request;

import com.example.iworks.global.dto.SearchConditionDate;
import lombok.*;

import java.util.List;

@Getter
public class ScheduleAssignSearchParameterAndDateRequestDto {
    private List<ScheduleAssignSearchParameterDto> searchParameterDto;
    private SearchConditionDate searchConditionDate;
}
