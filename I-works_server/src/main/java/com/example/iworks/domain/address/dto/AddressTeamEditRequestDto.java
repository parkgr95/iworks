package com.example.iworks.domain.address.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddressTeamEditRequestDto {
    private String teamName;
    private String teamDescription;
    private int teamLeaderId;

}
