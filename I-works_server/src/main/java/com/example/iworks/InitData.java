package com.example.iworks;

import com.example.iworks.domain.code.entity.Code;
import com.example.iworks.domain.code.entity.CodeGroup;
import com.example.iworks.domain.department.domain.Department;
import com.example.iworks.domain.schedule.domain.Schedule;
import com.example.iworks.domain.schedule.domain.ScheduleAssign;
import com.example.iworks.domain.team.domain.Team;
import com.example.iworks.domain.user.domain.User;
import com.example.iworks.global.util.RandomStringUtil;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Profile("local")
@Component
@RequiredArgsConstructor
@Slf4j
public class InitData {

    private final InitDataService initDataService;
    private final static RandomStringUtil RANDOM_STRING_UTIL = new RandomStringUtil();

    @PostConstruct
    public void init(){
        log.info("initDate {} ", " profile: local ");
        initDataService.init();
    }

    private static String makeRandomPassword(){
        int length = (int) (Math.random() * (12 - 8 + 1)) +8; // 8~12 길이
        return RANDOM_STRING_UTIL.getRandomPassword(length);
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
            Code codeAll = null;
            Code ScheduleDivisionTask = null; //할일 분류 : 업무
            Code ScheduleDivisionVacation = null; //할일 분류 : 휴가
            Code ScheduleDivisionSick = null; //할일 분류 : 병가
            Code test =null;
            List<User> userList = new ArrayList<>();
            List<Team> teamList = new ArrayList<>();
            List<Department> departmentList = new ArrayList<>();
            String [] codeNameList = {"카테고리","타겟","상태","직급", "할일분류"};
            //코드 그룹, 코드 데이터
            for (String codeName : codeNameList){
                em.persist(CodeGroup.builder()
                        .codeGroupName(codeName)
                        .build());
            }
            CodeGroup codeGroup1 = em.find(CodeGroup.class, 1);
            test = Code.builder()
                    .codeName("할일")
                    .codeGroup(codeGroup1)
                    .build();
            em.persist(test);
            test = Code.builder()
                    .codeName("게시판")
                    .codeGroup(codeGroup1)
                    .build();
            em.persist(test);
            test = Code.builder()
                    .codeName("미팅")
                    .codeGroup(codeGroup1)
                    .build();
            em.persist(test);



            CodeGroup codeGroup2 = em.find(CodeGroup.class, 2);

            codeUser = new Code();
            codeUser.setCodeName("전체");
            codeUser.setCodeGroup(codeGroup2);
            em.persist(codeUser);

            codeUser = new Code();
            codeUser.setCodeName("유저");
            codeUser.setCodeGroup(codeGroup2);
            em.persist(codeUser);

            codeDepartment = new Code();
            codeDepartment.setCodeName("부서");
            codeDepartment.setCodeGroup(codeGroup2);
            em.persist(codeDepartment);

            codeTeam = new Code();
            codeTeam.setCodeName("팀");
            codeTeam.setCodeGroup(codeGroup2);
            em.persist(codeTeam);

            CodeGroup codeGroup3 = em.find(CodeGroup.class, 3);

            ScheduleDivisionTask = Code.builder()
                    .codeName("온라인")
                    .codeGroup(codeGroup3)
                    .build();
            em.persist(ScheduleDivisionTask);

            test = Code.builder()
                    .codeName("외출중")
                    .codeGroup(codeGroup3)
                    .build();
            em.persist(test);

            ScheduleDivisionVacation = Code.builder()
                    .codeName("휴가중")
                    .codeGroup(codeGroup3)
                    .build();
            em.persist(ScheduleDivisionVacation);

            test = Code.builder()
                    .codeName("오프라인")
                    .codeGroup(codeGroup3)
                    .build();
            em.persist(test);

            CodeGroup codeGroup4 = em.find(CodeGroup.class, 4);

            Code code_role_employee = Code.builder()
                    .codeName("사원")
                    .codeGroup(codeGroup4)
                    .build();
            em.persist(code_role_employee);



            test = Code.builder()
                    .codeName("리더")
                    .codeGroup(codeGroup4)
                    .build();
            em.persist(test);

            test = Code.builder()
                    .codeName("CEO")
                    .codeGroup(codeGroup4)
                    .build();
            em.persist(test);


            test = Code.builder()
                    .codeName("관리자")
                    .codeGroup(codeGroup4)
                    .build();
            em.persist(test);

            CodeGroup codeGroup5 = em.find(CodeGroup.class, 5);
            em.persist(Code.builder()
                    .codeName("업무")
                    .codeGroup(codeGroup5)
                    .build());

            em.persist(Code.builder()
                    .codeName("행사")
                    .codeGroup(codeGroup5)
                    .build());

            em.persist(Code.builder()
                    .codeName("개인일정(병가)")
                    .codeGroup(codeGroup5)
                    .build());

            em.persist(Code.builder()
                    .codeName("개인일정(휴가)")
                    .codeGroup(codeGroup5)
                    .build());

            //부서별 유저 데이터
            for (int i = 1; i <= 5; i++){
                String departmentName = "부서" + i;
                Department department = Department.builder()
                        .departmentName(departmentName)
                        .departmentLeaderId(1)
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
                            .userPositionCode(code_role_employee)
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
                    .scheduleStartDate(LocalDateTime.now())
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
            scheduleByDepartment.addScheduleAssign(scheduleAssignDept);


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
                        .scheduleStartDate(LocalDateTime.now())
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
                    schedule.addScheduleAssign(scheduleAssign);
                }
                //모든 부서를 할일에 배정
                for (Department department : departmentList){
//
                    ScheduleAssign scheduleAssign =
                            ScheduleAssign.builder()
                                    .scheduleAssigneeCategory(codeUser)
                                    .scheduleAssigneeId(department.getDepartmentId())
                                    .build();
                    schedule.addScheduleAssign(scheduleAssign);

                }
            }

        }

    }

}