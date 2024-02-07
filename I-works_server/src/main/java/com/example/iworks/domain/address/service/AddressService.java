package com.example.iworks.domain.address.service;

import com.example.iworks.domain.address.dto.request.AddressTeamCreateRequestDto;
import com.example.iworks.domain.address.dto.request.AddressTeamEditRequestDto;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface AddressService {
    ResponseEntity<Map<String,Object>> selectAddressAll();

    ResponseEntity<Map<String,Object>> createTeam(String token, AddressTeamCreateRequestDto requestDto);

    ResponseEntity<Map<String,Object>> selectDepartmentAll();

    ResponseEntity<Map<String,Object>> selectTeamAll();

    ResponseEntity<Map<String, Object>> deleteTeam(int teamId, String token);

    ResponseEntity<Map<String, Object>> addTeamUser(int teamId, String token, int targetId);

    ResponseEntity<Map<String, Object>> removeTeamUser(int teamId, String token, int targetId);

    ResponseEntity<Map<String, Object>> editTeam(int teamId, String token, AddressTeamEditRequestDto requestDto);

    ResponseEntity<Map<String, Object>> getTeamInfo(int teamId);
}
