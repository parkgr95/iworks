package com.example.iworks.domain.admin.service.adminTeam;

import com.example.iworks.domain.address.dto.request.AddressTeamEditRequestDto;
import com.example.iworks.domain.address.dto.request.AddressTeamUserAddRequestDto;
import com.example.iworks.domain.admin.dto.adminTeam.response.AdminTeamResponseDto;
import com.example.iworks.domain.team.domain.Team;
import com.example.iworks.domain.team.domain.TeamUser;
import com.example.iworks.domain.team.repository.team.TeamRepository;
import com.example.iworks.domain.team.repository.teamuser.TeamUserRepository;
import com.example.iworks.domain.user.domain.User;
import com.example.iworks.domain.user.repository.UserRepository;
import com.example.iworks.global.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminTeamServiceImpl implements AdminTeamService{

    private final TeamRepository teamRepository;
    private final TeamUserRepository teamUserRepository;
    private final UserRepository userRepository;
    private final Response response;

    @Override
    public ResponseEntity<Map<String, Object>> getTeamAll() {
        List<AdminTeamResponseDto> result = teamRepository.findAll()
                .stream()
                .map(AdminTeamResponseDto::new)
                .collect(toList());
        return response.handleSuccess(result);
    }

    @Transactional
    @Override
    public ResponseEntity<Map<String, Object>> deleteTeam(int teamId) {
        Team team = teamRepository.findByTeamId(teamId);
        if (team == null || team.getTeamIsDeleted()) {
            return response.handleFail("팀을 찾을 수 없습니다.", null);
        }

        team.delete();
        return response.handleSuccess("삭제되었습니다.");
    }

    @Transactional
    @Override
    public ResponseEntity<Map<String, Object>> addTeamUser(int teamId, AddressTeamUserAddRequestDto requestDto) {

        System.out.println("dto : "+requestDto.getUserIds());

        Team team = teamRepository.findByTeamId(teamId);
        List<User> userList = userRepository.getUserListByUserIds(requestDto.getUserIds());
        System.out.println("userList : "+ userList);
        List<TeamUser> teamUserList = new ArrayList<>();
        if (userList.isEmpty()) {
            return response.handleFail("존재하지 않는 팀원입니다.", null);
        }
        if (team == null || team.getTeamIsDeleted()) {
            return response.handleFail("팀을 찾을 수 없습니다.", null);
        }

        for (User user : userList) {
            if (user.getUserIsDeleted()) {
                return response.handleFail(user.getUserName()+"님은 탈퇴한 회원입니다.", null);
            }

            for (TeamUser tm : team.getTeamUsers()) {
                if (tm.getTeamUserUser().getUserId() == user.getUserId()) {
                    return response.handleFail(user.getUserName()+"님은 이미 팀원입니다.", null);
                }
            }

            TeamUser teamUser = TeamUser.builder().teamUserUser(user).build();
            team.addTeamUser(teamUser);
            teamUserList.add(teamUser);
        }
        teamUserRepository.saveAll(teamUserList);
        return response.handleSuccess("팀원이 추가 되었습니다.");
    }

    @Transactional
    @Override
    public ResponseEntity<Map<String, Object>> removeTeamUser(int teamId, int targetId) {
        User target = userRepository.findByUserId(targetId);
        Team team = teamRepository.findByTeamId(teamId);

        if (target == null || target.getUserIsDeleted()) {
            return response.handleFail("유저를 찾을 수 없습니다.", null);
        }

        if (team == null || team.getTeamIsDeleted()) {
            return response.handleFail("팀을 찾을 수 없습니다.", null);
        }

        Optional<TeamUser> teamUser = team.getTeamUsers().stream().filter(tu -> tu.getTeamUserUser().getUserId() == targetId).findFirst();
        try {
            team.removeTeamUser(teamUser.get());
            teamUserRepository.delete(teamUser.get());
        } catch (Exception e) {
            return response.handleFail("존재하지 않는 팀원입니다.", null);
        }
        return response.handleSuccess("팀원이 삭제 되었습니다.");
    }

    @Transactional
    @Override
    public ResponseEntity<Map<String, Object>> editTeam(int teamId, AddressTeamEditRequestDto requestDto) {

        Team team = teamRepository.findByTeamId(teamId);

        if (team == null || team.getTeamIsDeleted()) {
            return response.handleFail("팀을 찾을 수 없습니다.", null);
        }

        team.update(requestDto);
        return response.handleSuccess("변경 완료");
    }

}
