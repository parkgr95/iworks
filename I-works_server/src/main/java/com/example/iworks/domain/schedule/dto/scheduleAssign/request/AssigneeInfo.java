package com.example.iworks.domain.schedule.dto.scheduleAssign.request;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

@NoArgsConstructor
@Getter
@Builder
@EqualsAndHashCode
public class AssigneeInfo {

   private int categoryCodeId; // 담당자 카테고리 아이디
   private int assigneeId; // 담당자 아이디

   @QueryProjection
    public AssigneeInfo(int categoryCodeId, int assigneeId) {
        this.categoryCodeId = categoryCodeId;
        this.assigneeId = assigneeId;
    }
}
