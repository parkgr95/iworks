package com.example.iworks.domain.address.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddressTeamCreateRequestDto {
    private String teamName;
    private int teamLeader;
    private String teamDescription;
}
