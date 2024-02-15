package com.example.iworks.domain.admin.service.adminTeam;

import com.example.iworks.domain.address.dto.response.AddressTeamInfoResponseDto;
import com.example.iworks.domain.admin.dto.adminTeam.request.AdminTeamCreateRequestDto;
import com.example.iworks.domain.admin.dto.adminTeam.request.AdminTeamUpdateRequestDto;
import com.example.iworks.domain.admin.dto.adminTeam.response.AdminTeamResponseDto;
import com.example.iworks.domain.team.domain.Team;
import com.example.iworks.domain.team.repository.team.TeamRepository;
import com.example.iworks.global.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AdminTeamServiceImpl implements AdminTeamService {

    private final TeamRepository teamRepository;
    private final Response response;

    private final PageRequest pageRequest = PageRequest.of(0, 10);

    @Transactional
    @Override
    public void createTeam(AdminTeamCreateRequestDto requestDto) {
        teamRepository.save(requestDto.toEntity());
    }

    @Transactional
    @Override
    public void updateTeam(int teamId, AdminTeamUpdateRequestDto requestDto) {
        Team findTeam = teamRepository.findById(teamId)
                .orElseThrow(IllegalStateException::new);
        findTeam.update(requestDto);
    }

    @Transactional
    @Override
    public void deleteTeam(int teamId) {
        Team findTeam = teamRepository.findById(teamId)
                .orElseThrow(IllegalStateException::new);
        findTeam.delete();
    }

    @Override
    public List<AdminTeamResponseDto> getTeamAll() {
        return teamRepository.findAll(pageRequest)
                .stream()
                .map(AdminTeamResponseDto::new)
                .collect(toList());
    }

    @Override
    public ResponseEntity<?> getTeam(int teamId) {
        Team team = teamRepository.findByTeamId(teamId);
        if (team != null) {
            return response.handleSuccess(new AddressTeamInfoResponseDto(team));
        }
        return response.handleFail("팀을 찾을 수 없습니다.", null);
    }
}
