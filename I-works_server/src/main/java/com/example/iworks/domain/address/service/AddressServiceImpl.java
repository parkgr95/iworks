package com.example.iworks.domain.address.service;

import com.example.iworks.domain.address.dto.*;
import com.example.iworks.domain.address.respository.AddressRepository;
import com.example.iworks.domain.department.domain.Department;
import com.example.iworks.domain.department.repository.DepartmentRepository;
import com.example.iworks.domain.team.domain.Team;
import com.example.iworks.domain.team.domain.TeamUser;
import com.example.iworks.domain.team.repository.team.TeamRepository;
import com.example.iworks.domain.team.repository.teamuser.TeamUserRepository;
import com.example.iworks.domain.user.domain.User;
import com.example.iworks.domain.user.repository.UserRepository;
import com.example.iworks.global.model.Response;
import com.example.iworks.global.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final DepartmentRepository departmentRepository;
    private final TeamRepository teamRepository;
    private final TeamUserRepository teamUserRepository;
    private final UserRepository userRepository;
    private final Response response;
    private final JwtProvider jwtProvider;

    @Override
    public ResponseEntity<Map<String,Object>> selectAddressAll()
    {
        List<AddressDto> list =  addressRepository.selectAddressAll();
        if(list.isEmpty()){
            return response.handleFail("조회 내용 없음.",null);
        }

        Stream<AddressUserResponseDto> result = list.stream()
                .filter(item-> !item.getUser().getUserIsDeleted()).map(AddressUserResponseDto::new);

        return response.handleSuccess(result);
    }

    @Transactional
    @Override
    public ResponseEntity<Map<String,Object>> createTeam(AddressTeamCreateRequestDto requestDto)
    {
        try {
            Team team = new Team(requestDto);
            if(teamRepository.findByTeamName(team.getTeamName())!= null){
                return response.handleFail("이미 존재하는 팀 이름입니다.",null);
            }
            Team result = teamRepository.save(team);
            HashMap<String,Object> map = new HashMap<>();
            map.put("teamId",result.getTeamId());
            map.put("message","팀이 생성 되었습니다.");
            return response.handleSuccess(map);
        }catch (Exception e){
            return response.handleFail("생성 실패",e.getMessage());
        }
    }



    @Override
    public ResponseEntity<Map<String, Object>> selectDepartmentAll() {
        Stream<AddressDepartmentResonseDto> result = departmentRepository.findAll().stream()
                .filter(Department::isDepartmentIsUsed).map(AddressDepartmentResonseDto::new);
        return response.handleSuccess(result);
    }

    @Override
    public ResponseEntity<Map<String, Object>> selectTeamAll() {
        Stream<AddressTeamResponseDto> result = teamRepository.findAll().stream()
                .filter(Team::getTeamIsDeleted).map(AddressTeamResponseDto::new);
        return response.handleSuccess(result);
    }

    @Transactional
    @Override
    public ResponseEntity<Map<String, Object>> deleteTeam(int teamId, String token) {
        int userId = jwtProvider.getUserId(token);
        Team team = teamRepository.findByTeamId(teamId);

        if(team == null){
            return  response.handleFail("팀을 찾을 수 없습니다.",null);
        }

        if(team.getTeamLeader() != userId){
            return response.handleFail("팀의 리더가 아닙니다.",null);
        }
        teamRepository.delete(team);
        return response.handleSuccess("삭제되었습니다.");
    }

    @Transactional
    @Override
    public ResponseEntity<Map<String, Object>> addTeamUser(int teamId, String token, int targetId) {
            int userId = jwtProvider.getUserId(token);
            User user = userRepository.findByUserId(userId);
            Team team = teamRepository.findByTeamId(teamId);

            if (user == null) {
                return response.handleFail("유저를 찾을 수 없습니다.", null);
            }

            if (team == null) {
                return response.handleFail("팀을 찾을 수 없습니다.", null);
            }

            if (team.getTeamLeader() != userId) {
                return response.handleFail("팀의 리더가 아닙니다.", null);
            }
            TeamUser teamUser = TeamUser.builder().teamUserUser(user).build();

            for(TeamUser tm : team.getTeamUsers()){
                if(tm.getTeamUserUser().getUserId() == userId){
                    return response.handleFail("이미 존재하는 팀원입니다.", null);
                }
            }
            team.addTeamUser(teamUser);
            teamUserRepository.save(teamUser);
            return response.handleSuccess("팀원이 추가 되었습니다.");
    }

    @Transactional
    @Override
    public ResponseEntity<Map<String, Object>> removeTeamUser(int teamId, String token, int targetId) {
        int userId = jwtProvider.getUserId(token);
        User user = userRepository.findByUserId(userId);
        Team team = teamRepository.findByTeamId(teamId);

        if (user == null) {
            return response.handleFail("유저를 찾을 수 없습니다.", null);
        }

        if (team == null) {
            return response.handleFail("팀을 찾을 수 없습니다.", null);
        }

        if (team.getTeamLeader() != userId) {
            return response.handleFail("팀의 리더가 아닙니다.", null);
        }
        Optional<TeamUser> teamUser = team.getTeamUsers().stream().filter(tu->tu.getTeamUserUser().getUserId() == userId).findFirst();
        try {
            team.removeTeamUser(teamUser.get());
            teamUserRepository.delete(teamUser.get());
        }catch (Exception e){
            return  response.handleFail("존재하지 않는 팀원입니다.",null);
        }
        return response.handleSuccess("팀원이 삭제 되었습니다.");
    }

}
