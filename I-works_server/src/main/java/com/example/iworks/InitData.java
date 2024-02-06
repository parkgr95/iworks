package com.example.iworks;

import com.example.iworks.domain.department.domain.Department;
import com.example.iworks.domain.schedule.domain.Schedule;
import com.example.iworks.domain.schedule.domain.ScheduleAssign;
import com.example.iworks.domain.team.domain.Team;
import com.example.iworks.domain.user.domain.User;
import com.example.iworks.global.model.entity.Code;
import com.example.iworks.global.model.entity.CodeGroup;
import com.example.iworks.global.util.RandomPasswordUtil;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


//@Profile("local")
@Component
@RequiredArgsConstructor
@Slf4j
public class InitData {

    private final InitDataService initDataService;
    private final static RandomPasswordUtil randomPasswordUtil  = new RandomPasswordUtil();

    @PostConstruct
    public void init(){
        log.info("initDate {} ", " profile: local ");
        initDataService.init();
    }

    private static String makeRandomPassword(){
        int length = (int) (Math.random() * (12 - 8 + 1)) +8; // 8~12 길이
        return randomPasswordUtil.getRandomPassword(length);
    }
    private static Long Eid = 1L; //락 걸기?

    private static String generateEid(){
        StringBuilder EidStr = new StringBuilder(Eid++ + "");
        if (EidStr.length() > 6){
            EidStr.setLength(6);
            return EidStr.toString();
        }
        if(EidStr.length() < 6){
            return String.format("%06d", Long.parseLong(EidStr.toString()));
        }
        if (EidStr.length() == 6)
            return EidStr.toString();
        return null;
    }

    @Component
    static class InitDataService {
        @PersistenceContext private EntityManager em;

        @Transactional
        public void init(){
            int scheduleSeq = 1;
            int userSeq = 1;
            int userEmailSeq = 1;
            Code codeDepartment = null;
            Code codeUser = null;
            Code codeTeam = null;
            Code ScheduleDivisionTask = null; //할일 분류 : 업무
            Code ScheduleDivisionVacation = null; //할일 분류 : 휴가
            Code ScheduleDivisionSick = null; //할일 분류 : 병가
            List<User> userList = new ArrayList<>();
            List<Team> teamList = new ArrayList<>();
            List<Department> departmentList = new ArrayList<>();

            //코드 그룹, 코드 데이터
            for (int i =1; i <= 3; i++){
                em.persist(CodeGroup.builder()
                        .codeGroupName("코드그룹"+ i)
                        .build());
            }
            CodeGroup codeGroup1 = em.find(CodeGroup.class, 1);
            System.out.println(codeGroup1);

            codeUser = new Code();
            codeUser.setCodeName("유저");
            codeUser.setCodeGroup(codeGroup1);
            em.persist(codeUser);

            codeDepartment = new Code();
            codeDepartment.setCodeName("부서");
            codeDepartment.setCodeGroup(codeGroup1);
            em.persist(codeDepartment);

            codeTeam = new Code();
            codeTeam.setCodeName("팀");
            codeTeam.setCodeGroup(codeGroup1);
            em.persist(codeTeam);

            CodeGroup codeGroup2 = em.find(CodeGroup.class, 2);

            ScheduleDivisionTask = Code.builder()
                    .codeName("업무")
                    .codeCodeGroup(codeGroup2)
                    .build();
            em.persist(ScheduleDivisionTask);

            ScheduleDivisionVacation = Code.builder()
                    .codeName("개인일정(휴가)")
                    .codeCodeGroup(codeGroup2)
                    .build();
            em.persist(ScheduleDivisionVacation);

            ScheduleDivisionSick = Code.builder()
                    .codeName("개인일정(병가)")
                    .codeCodeGroup(codeGroup2)
                    .build();
            em.persist(ScheduleDivisionSick);

            //부서별 유저 데이터
            for (int i = 1; i <= 5; i++){
                String departmentName = "부서" + i;
                Department department = Department.builder()
                        .departmentName(departmentName)
                        .build();
                em.persist(department);
                departmentList.add(department);

                //부서 별 유저 데이터
                for (int j = 1; j <= 20; j++){
                    User user = User.builder()
                            .userEid(generateEid())
                            .userPassword(makeRandomPassword())
                            .userEmail(userEmailSeq+++"")
                            .userNameFirst("유저"+userSeq)
                            .userDepartment(department)
                            .build();
                    em.persist(user);
                    userList.add(user);
                }
            }
            User user1 = em.find(User.class, 1);
            Department department1 = em.find(Department.class, 1);
            //부서 1에 할일 생성
            Schedule scheduleByDepartment = Schedule.builder()
                    .scheduleDivision(codeUser)
                    .scheduleTitle("부서의 할일"+ scheduleSeq)
                    .scheduleEndDate(LocalDateTime.now())
                    .scheduleCreator(user1)
                    .build();
            em.persist(scheduleByDepartment);
            //부서 1에 할일 배정
            ScheduleAssign scheduleAssignDept =
                    ScheduleAssign.builder()
                            .scheduleAssigneeCategory(codeDepartment)
                            .scheduleAssigneeId(department1.getDepartmentId())
                            .build();
            scheduleByDepartment.addScheduleAssigns(scheduleAssignDept);


            //팀 데이터
            for (int i= 1; i < 4; i++){
                Team team = Team.builder()
                        .teamName(i+"팀")
                        .teamCreator(1)
                        .teamLeader(1)
                        .build();
                em.persist(team);
            }


            //할 일 3개 생성
            for (int i = 0; i <= 3; i++){
                Schedule schedule = Schedule.builder()
                        .scheduleDivision((i & 1)==1?ScheduleDivisionTask: ScheduleDivisionVacation)
                        .scheduleTitle("할일"+ scheduleSeq)
                        .scheduleEndDate(LocalDateTime.now())
                        .scheduleCreator(user1)
                        .build();

                em.persist(schedule);
                //모든 유저를 할일에 배정
                for (User user : userList){
//
                    ScheduleAssign scheduleAssign =
                            ScheduleAssign.builder()
                                    .scheduleAssigneeCategory(codeUser)
                                    .scheduleAssigneeId(user.getUserId())
                                    .build();
                    schedule.addScheduleAssigns(scheduleAssign);
                }
                //모든 부서를 할일에 배정
                for (Department department : departmentList){
//
                    ScheduleAssign scheduleAssign =
                            ScheduleAssign.builder()
                                    .scheduleAssigneeCategory(codeUser)
                                    .scheduleAssigneeId(department.getDepartmentId())
                                    .build();
                    schedule.addScheduleAssigns(scheduleAssign);

                }
            }

        }

    }

}