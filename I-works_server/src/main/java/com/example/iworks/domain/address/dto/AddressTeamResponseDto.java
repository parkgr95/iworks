package com.example.iworks.domain.address.dto;

import com.example.iworks.domain.team.domain.Team;
import lombok.Getter;

@Getter
public class AddressTeamResponseDto {
    private final String teamName;
    private final Integer teamId;

    public AddressTeamResponseDto(Team team){
        this.teamName=team.getTeamName();
        this.teamId=team.getTeamId();
    }
}
