package com.example.iworks.domain.admin.service.adminSchedule;

import com.example.iworks.domain.admin.dto.adminSchedule.AdminScheduleResponseDto;

import java.util.List;

public interface AdminScheduleService {
    List<AdminScheduleResponseDto> getScheduleAll();

}
