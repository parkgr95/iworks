package com.example.iworks.domain.admin.service.adminTeam;

import com.example.iworks.domain.admin.dto.adminTeam.request.AdminTeamCreateRequestDto;
import com.example.iworks.domain.admin.dto.adminTeam.request.AdminTeamUpdateRequestDto;
import com.example.iworks.domain.admin.dto.adminTeam.response.AdminTeamResponseDto;

import java.util.List;

public interface AdminTeamService {

    // 팀 등록
    void createTeam(AdminTeamCreateRequestDto requestDto);

    // 팀 수정
    void updateTeam(int teamId, AdminTeamUpdateRequestDto requestDto);

    // 팀 삭제
    void deleteTeam(int teamId);

    // 팀 전체 조회
    List<AdminTeamResponseDto> getTeamAll();

    // 팀 세부 조회
    AdminTeamResponseDto getTeam(int teamId);
}
