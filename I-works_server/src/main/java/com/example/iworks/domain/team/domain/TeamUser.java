package com.example.iworks.domain.team.domain;
import com.example.iworks.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "team_user")
@Getter
@Builder @AllArgsConstructor @NoArgsConstructor
public class TeamUser {

    @Id @GeneratedValue
    @Column(name = "team_user_id")
    private Integer teamUserId;

    @ManyToOne
    @JoinColumn(name = "team_id", updatable = false)
    private Team teamUserTeamId; // 팀 아이디

    @ManyToOne
    @JoinColumn(name = "team_user_user_id", updatable = false)
    private User teamUserUser; // 유저 아이디

    public void setTeamUserTeamId(Team team){
        this.teamUserTeamId = team;
    }

}
