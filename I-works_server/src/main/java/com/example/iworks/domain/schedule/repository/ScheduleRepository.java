package com.example.iworks.domain.schedule.repository;

import com.example.iworks.domain.schedule.domain.Schedule;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

    //개인 별 할 일 검색
    //개인 별, 정보를 먼저 조회하고, 유저가 포함된 부서, 팀을 조회
    //할 일 배정 테이블에서 개인별 할일, 부서별 할일, 팀별 할일을 조회
    //한꺼번에 묶어서 보내주어야 함.
    @Query("select s from Schedule s where s.scheduleStartDate between :startDate and :endDate")
    List<Schedule> findByScheduleStartDate (LocalDateTime startDate, LocalDateTime endDate);




}
