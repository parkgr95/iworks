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

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

    //개인 별 할 일 검색
    //개인 별, 정보를 먼저 조회하고, 유저가 포함된 부서, 팀을 조회
    //할 일 배정 테이블에서 개인별 할일, 부서별 할일, 팀별 할일을 조회
    //한꺼번에 묶어서 보내주어야 함.
    @Query("select s from Schedule s where s.scheduleStartDate between :startDate and :endDate")
    List<Schedule> findByScheduleStartDate (LocalDateTime startDate, LocalDateTime endDate);

    //1. 유저의 id, 유저가 속한 팀의 아이디, 유저가 속한 부서의 아이디를 조회
    //2. 동적 쿼리 : 할일배정테이블에서 [할일 배정자 카테고리 코드 아이디(유저: 1), 배정자 id]에 해당하는 할일 정보 조회(할일 아이디, 할일분류 아이디, 할일 이름, 시작일시, 종료) 단, 완료여부 false인 할 일만 조회
//    @Query("select new dto.AssigneesScheduleRequestDto(scheduleCategoryCodeId, scheduleAssigneeId ) "+
//            "from ScheduleAssign sa join")
//    List<Schedule> findSchedulesBySchedule();

//private int scheduleCategoryCodeId;
//   private int scheduleAssigneeId;
}
