package com.example.iworks.domain.admin.dto.adminTeam.response;

import com.example.iworks.domain.team.domain.Team;

import java.time.LocalDateTime;

public class AdminTeamResponseDto {
    private final int teamId; // 팀 아이디
    private final String teamName; // 팀명
    private final int teamLeader; // 팀장 아이디
    private final String teamDescription; // 팀 설명
    private final int teamCreator; // 팀 생성자
    private final LocalDateTime teamCreatedAt; // 팀 생성일시
    private final LocalDateTime teamUpdatedAt; // 팀 수정일시

    public AdminTeamResponseDto(Team team) {
        this.teamId = team.getTeamId();
        this.teamName = team.getTeamName();
        this.teamLeader = team.getTeamLeader();
        this.teamDescription = team.getTeamDescription();
        this.teamCreator = team.getTeamCreator();
        this.teamCreatedAt = team.getTeamCreatedAt();
        this.teamUpdatedAt = team.getTeamUpdatedAt();
    }
}
