package com.example.iworks.domain.schedule.repository.scheduleAssign;

import com.example.iworks.domain.schedule.domain.ScheduleAssign;
import com.example.iworks.domain.schedule.repository.scheduleAssign.custom.ScheduleAssignRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleAssignRepository extends JpaRepository<ScheduleAssign, Integer>, ScheduleAssignRepositoryCustom {

}
