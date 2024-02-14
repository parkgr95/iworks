package com.example.iworks.domain.schedule.dto.scheduleAssign.request;

import com.example.iworks.domain.code.entity.Code;
import com.example.iworks.domain.schedule.domain.ScheduleAssign;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@Getter
@Builder @AllArgsConstructor
@EqualsAndHashCode
public class AssigneeInfo {

    @NotNull
    private Integer categoryCodeId; // 담당자 카테고리 아이디

    @NotNull
    private Integer assigneeId; // 담당자 아이디

    public ScheduleAssign toScheduleAssignEntity(Code categeryCode) {
        return ScheduleAssign.builder()
                .scheduleAssigneeCategory(categeryCode)
                .scheduleAssigneeId(this.assigneeId)
                .build();
    }
}
