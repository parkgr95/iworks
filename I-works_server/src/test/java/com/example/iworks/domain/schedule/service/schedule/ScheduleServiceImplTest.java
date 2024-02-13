package com.example.iworks.domain.schedule.service.schedule;

import com.example.iworks.domain.notification.service.UserNotificationService;
import com.example.iworks.domain.notification.service.UserNotificationServiceImpl;
import com.example.iworks.domain.schedule.domain.Schedule;
import com.example.iworks.domain.schedule.dto.schedule.request.ScheduleCreateRequestDto;
import com.example.iworks.domain.schedule.dto.scheduleAssign.request.AssigneeInfo;
import com.example.iworks.domain.schedule.repository.schedule.ScheduleRepository;
import com.example.iworks.domain.schedule.repository.schedule.ScheduleRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:env.properties")
class ScheduleServiceImplTest {

    @Value("${MYSQL_USER}")
    private String mysqlUser;

    @Value("${MYSQL_PASSWORD}")
    private String mysqlPassword;

    @Value("${REDIS_HOST:localhost}")
    private String redisHost;

    @Value("${REDIS_PORT:6379}")
    private String redisPort;

    @Value("${JWT_SECRET_KEY:your_default_secret}")
    private String jwtSecret;
    @InjectMocks
    private ScheduleServiceImpl scheduleService;

    @Mock
    private ScheduleRepositoryImpl scheduleRepository;

    @Mock
    private JpaRepository<Schedule, Long> scheduleJpaRepository; // Change to JpaRepository

    @Mock
    private UserNotificationServiceImpl userNotificationService;

    @Test
    void 할일생성_성공_알림생성_실패() {
        // Given
        // Prepare your createRequestDto with necessary data
//        ScheduleCreateRequestDto createRequestDto = new ScheduleCreateRequestDto(
//                1, // scheduleDivisionCodeId
//                "Sample Schedule", // scheduleTitle
//                'H', // schedulePriority
//                "Sample content", // scheduleContent
//                LocalDateTime.now(), // scheduleStartDate
//                LocalDateTime.now().plusHours(2), // scheduleEndDate
//                "Sample Place", // schedulePlace
//                LocalDateTime.now().plusHours(1), // meetingDate
//                Arrays.asList( // assigneeInfos
//                        new AssigneeInfo(1, 1), // Assuming assignee category id and assignee id
//                        new AssigneeInfo(1, 102)
//                )
//        );

        // Stubbing behavior for the repository methods
        when(scheduleJpaRepository.save(any())).thenAnswer(invocation -> {
            Schedule schedule = invocation.getArgument(0);
            try {
                Field idField = Schedule.class.getDeclaredField("scheduleId");
                idField.setAccessible(true);
                idField.set(schedule, 1); // Setting some id for stubbing
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return schedule;
        });

        // Stubbing behavior for notification service
        doThrow(new RuntimeException("Notification creation failed")).when(userNotificationService).create(any());

        // When
        // Call the method under test
//        assertThrows(RuntimeException.class, () -> scheduleService.createSchedule(1, createRequestDto));

        // Then
        // Ensure that scheduleRepository.save() was not called
        verify(scheduleJpaRepository, never()).save(any());
    }
}