package com.example.iworks.domain.admin.controller;

import com.example.iworks.domain.address.dto.request.AddressTeamCreateRequestDto;
import com.example.iworks.domain.address.dto.request.AddressTeamEditRequestDto;
import com.example.iworks.domain.address.dto.request.AddressTeamUserAddRequestDto;
import com.example.iworks.domain.address.dto.request.AddressTeamUserRemoveRequestDto;
import com.example.iworks.domain.address.service.AddressService;

import com.example.iworks.domain.admin.service.adminTeam.AdminTeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/api/admin/team")
@RestController
@RequiredArgsConstructor
public class AdminTeamController {

    private final AddressService addressService;
    private final AdminTeamService adminTeamService;

    @GetMapping("/all")
    public ResponseEntity<Map<String,Object>> getTeamAll(){
        return addressService.selectTeamAll();
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String,Object>> createTeam(@RequestHeader(name = "Authorization")String token,@RequestBody AddressTeamCreateRequestDto requestDto){
        return addressService.createTeam(token,requestDto);
    }

    @PutMapping("/{teamId}")
    public ResponseEntity<Map<String,Object>> editTeam(@PathVariable(name = "teamId")int teamId, @RequestBody AddressTeamEditRequestDto requestDto){
        return adminTeamService.editTeam(teamId,requestDto);
    }

    @GetMapping("/{teamId}")
    public ResponseEntity<Map<String,Object>> getTeamInfo(@PathVariable(name = "teamId")int teamId){
        return addressService.getTeamInfo(teamId);
    }

    @DeleteMapping("/{teamId}")
    public ResponseEntity<Map<String,Object>> deleteTeam(@PathVariable(name = "teamId")int teamId){
        return adminTeamService.deleteTeam(teamId);
    }
    @PostMapping("/user/{teamId}")
    public ResponseEntity<Map<String,Object>> addTeamUser(@PathVariable(name = "teamId")int teamId, @RequestBody AddressTeamUserAddRequestDto userIds){
        return adminTeamService.addTeamUser(teamId,userIds);
    }

    @DeleteMapping("/user/{teamId}")
    public ResponseEntity<Map<String,Object>> removeTeamUser(@PathVariable(name = "teamId")int teamId, @RequestBody AddressTeamUserRemoveRequestDto requestDto){
        return adminTeamService.removeTeamUser(teamId,requestDto.getTargetId());
    }
}
