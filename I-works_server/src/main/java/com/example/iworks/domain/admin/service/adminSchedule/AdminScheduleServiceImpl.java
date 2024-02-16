package com.example.iworks.domain.admin.service.adminSchedule;

import com.example.iworks.domain.admin.dto.adminSchedule.AdminScheduleResponseDto;
import com.example.iworks.domain.schedule.repository.schedule.ScheduleRepository;
import com.example.iworks.domain.schedule.service.scheduleAssign.ScheduleAssignService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminScheduleServiceImpl implements AdminScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleAssignService scheduleAssignService;

    @Override
    public List<AdminScheduleResponseDto> getScheduleAll() {
        return scheduleRepository.findAll()
                .stream()
                .map(schedule -> new AdminScheduleResponseDto(
                        schedule,
                        scheduleAssignService.getAssigneeNameList(
                                schedule.getScheduleAssigns()))
                )
                .collect(Collectors.toList());
    }

}
