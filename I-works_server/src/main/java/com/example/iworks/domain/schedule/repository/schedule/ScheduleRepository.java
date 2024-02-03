package com.example.iworks.domain.schedule.repository.schedule;

import com.example.iworks.domain.schedule.domain.Schedule;
import com.example.iworks.domain.schedule.domain.ScheduleAssign;
import com.example.iworks.domain.schedule.repository.schedule.custom.ScheduleSearchRepository;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer>, ScheduleSearchRepository {
    Schedule findScheduleByScheduleTitle(String name);

}
