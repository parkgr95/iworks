package com.example.iworks.domain.admin.dto.adminTeam.response;

import com.example.iworks.domain.team.domain.Team;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AdminTeamResponseDto {
    private int teamId; // 팀 아이디
    private String teamName; // 팀명
    private int teamLeader; // 팀장 아이디
    private String teamDescription; // 팀 설명
    private int teamCreator; // 팀 생성자
    private LocalDateTime teamCreatedAt; // 팀 생성일시
    private LocalDateTime teamUpdatedAt; // 팀 수정일시

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
