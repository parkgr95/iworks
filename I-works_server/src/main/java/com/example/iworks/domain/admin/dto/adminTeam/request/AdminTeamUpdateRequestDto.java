package com.example.iworks.domain.admin.dto.adminTeam.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminTeamUpdateRequestDto {
    private String teamName;
    private int teamLeaderId;
    private String teamDescription;
}
