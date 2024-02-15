package com.example.iworks.domain.admin.service.adminTeam;

import com.example.iworks.domain.address.dto.request.AddressTeamEditRequestDto;
import com.example.iworks.domain.address.dto.request.AddressTeamUserAddRequestDto;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface AdminTeamService {

    ResponseEntity<Map<String, Object>> getTeamAll();

    ResponseEntity<Map<String, Object>> deleteTeam(int teamId);

    ResponseEntity<Map<String, Object>> addTeamUser(int teamId, AddressTeamUserAddRequestDto requestDto);

    ResponseEntity<Map<String, Object>> removeTeamUser(int teamId, int targetId);

    ResponseEntity<Map<String, Object>> editTeam(int teamId, AddressTeamEditRequestDto requestDto);
}
