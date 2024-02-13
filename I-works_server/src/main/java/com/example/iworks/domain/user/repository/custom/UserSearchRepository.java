package com.example.iworks.domain.user.repository.custom;

import com.example.iworks.domain.schedule.dto.scheduleAssign.request.AssigneeInfo;
import com.example.iworks.domain.user.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserSearchRepository {
    List<User> getUserListByUserIds(List<Integer> dto);
}
