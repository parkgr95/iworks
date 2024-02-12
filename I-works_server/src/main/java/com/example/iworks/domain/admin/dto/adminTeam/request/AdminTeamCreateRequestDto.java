package com.example.iworks.domain.admin.dto.adminTeam.request;

import com.example.iworks.domain.team.domain.Team;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminTeamCreateRequestDto {
    private String teamName;
    private int teamLeader;
    private String teamDescription;
    private int teamCreator;

    public Team toEntity() {
        return Team.builder()
                .teamName(teamName)
                .teamLeader(teamLeader)
                .teamDescription(teamDescription)
                .teamCreator(teamCreator)
                .teamCreatedAt(LocalDateTime.now())
                .teamUpdatedAt(LocalDateTime.now())
                .build();
    }
}
