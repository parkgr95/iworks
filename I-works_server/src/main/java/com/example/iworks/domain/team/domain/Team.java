package com.example.iworks.domain.team.domain;

import com.example.iworks.domain.address.dto.AddressTeamCreateRequestDto;
import com.example.iworks.domain.address.dto.AddressTeamEditRequestDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "team")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
public class Team {

    @Id
    @GeneratedValue
    @Column(name = "team_id")
    private int teamId; // 그룹 아이디

    @Column(name = "team_name", length = 20, nullable = false)
    private String teamName; // 그룹명

    @Column(name = "team_leader", nullable = false)
    private int teamLeader; // 그룹장

    @Column(name = "team_desc", length = 100)
    private String teamDescription; // 그룹 설명

    @Column(name = "team_creator", nullable = false)
    private int teamCreator; // 그룹 생성자

    @Builder.Default
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "team_created_at", nullable = false)
    private LocalDateTime teamCreatedAt = LocalDateTime.now(); // 그룹 생성일시

    @Builder.Default
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "team_updated_at", nullable = false)
    private LocalDateTime teamUpdatedAt = LocalDateTime.now(); // 그룹 수정일시

    @OneToMany(mappedBy = "teamUserTeam")
    private List<TeamUser> teamUsers;

    @Builder.Default
    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean teamIsDeleted = false;

    public Team(AddressTeamCreateRequestDto requestDto,int userId) {
        this.teamName = requestDto.getTeamName();
        this.teamCreator = userId;
        this.teamLeader = requestDto.getTeamLeader();
        this.teamDescription = requestDto.getTeamDescription();
        this.teamCreatedAt = LocalDateTime.now();
        this.teamUpdatedAt = LocalDateTime.now();
        this.teamIsDeleted = false;
    }

    public void addTeamUser(TeamUser teamUser) {
        teamUsers.add(teamUser);
        teamUser.setTeamUserTeamId(this);
    }


    public void removeTeamUser(TeamUser teamUser) {
        teamUsers.remove(teamUser);
    }

    public void update(AddressTeamEditRequestDto requestDto){
        this.teamName = requestDto.getTeamName();
        this.teamDescription = requestDto.getTeamDescription();
        this.teamLeader = requestDto.getTeamLeaderId();
        this.teamUpdatedAt = LocalDateTime.now();
    }

    public void delete(){
        this.teamIsDeleted = true;
        this.teamUpdatedAt = LocalDateTime.now();
    }

}
