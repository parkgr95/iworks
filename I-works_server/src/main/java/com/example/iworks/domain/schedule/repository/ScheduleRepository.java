package com.example.iworks.domain.schedule.repository;

import com.example.iworks.domain.schedule.domain.Schedule;
import com.example.iworks.domain.schedule.domain.ScheduleAssign;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

    Schedule findScheduleByScheduleTitle(String name);

    @Query("select s from Schedule s where s.scheduleStartDate between :startDate and :endDate")
    List<Schedule> findByScheduleStartDate (LocalDateTime startDate, LocalDateTime endDate);

}
