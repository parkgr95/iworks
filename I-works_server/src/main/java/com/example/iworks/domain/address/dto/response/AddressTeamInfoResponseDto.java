package com.example.iworks.domain.address.dto.response;

import com.example.iworks.domain.team.domain.Team;
import com.example.iworks.domain.team.domain.TeamUser;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class AddressTeamInfoResponseDto {
    private int teamId; // 그룹 아이디
    private String teamName; // 그룹명
    private int teamLeader; // 그룹장
    private String teamDescription; // 그룹 설명
    private int teamCreator; // 그룹 생성자
    private LocalDateTime teamCreatedAt = LocalDateTime.now(); // 그룹 생성일시
    private LocalDateTime teamUpdatedAt = LocalDateTime.now(); // 그룹 수정일시
    private List<TeamUser> teamUsers;

    public AddressTeamInfoResponseDto(Team team){
        this.teamId=team.getTeamId();
        this.teamName=team.getTeamName();
        this.teamLeader = team.getTeamLeader();
        this.teamDescription = team.getTeamDescription();
        this.teamCreator = team.getTeamCreator();
        this.teamCreatedAt = team.getTeamCreatedAt();
        this.teamUpdatedAt = team.getTeamUpdatedAt();
        this.teamUsers = team.getTeamUsers();
    }
}
