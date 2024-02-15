package com.example.iworks.domain.address.controller;

import com.example.iworks.domain.address.dto.request.AddressTeamCreateRequestDto;
import com.example.iworks.domain.address.dto.request.AddressTeamEditRequestDto;
import com.example.iworks.domain.address.dto.request.AddressTeamUserAddRequestDto;
import com.example.iworks.domain.address.dto.request.AddressTeamUserRemoveRequestDto;
import com.example.iworks.domain.address.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/api/address")
@RestController
@RequiredArgsConstructor
public class AddressApiController {
    private final AddressService addressService;


    @GetMapping("/department/all")
    public ResponseEntity<Map<String,Object>> getDepartmentAll(){
        return addressService.selectDepartmentAll();
    }

    @GetMapping("/team/all")
    public ResponseEntity<Map<String,Object>> getTeamAll(){
        return addressService.selectTeamAll();
    }

    @GetMapping("/team/my")
    public ResponseEntity<Map<String,Object>> getMyTeamAll(@RequestHeader("Authorization") String token){
        return addressService.selectMyTeamAll(token);
    }

    @GetMapping("/user/all")
    public ResponseEntity<Map<String,Object>> getAddressAll(){
        return addressService.selectAddressAll();
    }

    @PostMapping("/team/create")
    public ResponseEntity<Map<String,Object>> createTeam(@RequestHeader(name = "Authorization")String token,@RequestBody AddressTeamCreateRequestDto requestDto){
        return addressService.createTeam(token,requestDto);
    }

    @PutMapping("/team/{teamId}")
    public ResponseEntity<Map<String,Object>> editTeam(@PathVariable(name = "teamId")int teamId,@RequestHeader(name = "Authorization")String token,@RequestBody AddressTeamEditRequestDto requestDto){
        return addressService.editTeam(teamId,token,requestDto);
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<Map<String,Object>> getTeamInfo(@PathVariable(name = "teamId")int teamId){
        return addressService.getTeamInfo(teamId);
    }

    @DeleteMapping("/team/{teamId}")
    public ResponseEntity<Map<String,Object>> deleteTeam(@PathVariable(name = "teamId")int teamId, @RequestHeader(name = "Authorization")String token){

        return addressService.deleteTeam(teamId,token);
    }
    @PostMapping("/team/user/{teamId}")
    public ResponseEntity<Map<String,Object>> addTeamUser(@PathVariable(name = "teamId")int teamId, @RequestHeader(name = "Authorization")String token,@RequestBody AddressTeamUserAddRequestDto userIds){
        return addressService.addTeamUser(teamId,token,userIds);
    }

    @DeleteMapping("/team/user/{teamId}")
    public ResponseEntity<Map<String,Object>> removeTeamUser(@PathVariable(name = "teamId")int teamId, @RequestHeader(name = "Authorization")String token,@RequestBody AddressTeamUserRemoveRequestDto requestDto){
        return addressService.removeTeamUser(teamId,token,requestDto.getTargetId());
    }

}
