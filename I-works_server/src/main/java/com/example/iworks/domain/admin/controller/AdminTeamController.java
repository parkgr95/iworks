package com.example.iworks.domain.admin.controller;

import com.example.iworks.domain.admin.dto.adminTeam.request.AdminTeamCreateRequestDto;
import com.example.iworks.domain.admin.dto.adminTeam.request.AdminTeamUpdateRequestDto;
import com.example.iworks.domain.admin.service.adminTeam.AdminTeamService;
import com.example.iworks.global.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/admin/team")
@RestController
@RequiredArgsConstructor
public class AdminTeamController {

    private final AdminTeamService adminService;
    private final Response response;

    // 팀 등록
    @PostMapping("/")
    public ResponseEntity<?> createTeam(@RequestBody AdminTeamCreateRequestDto requestDto) {
        adminService.createTeam(requestDto);
        return response.handleSuccess("팀 등록 완료");
    }

    // 팀 수정
    @PutMapping("/{teamId}")
    public ResponseEntity<?> updateTeam(@PathVariable(name = "teamId") int teamId, @RequestBody AdminTeamUpdateRequestDto requestDto) {
        adminService.updateTeam(teamId, requestDto);
        return  response.handleSuccess("팀 수정 완료");
    }

    // 팀 삭제
    @DeleteMapping("/{teamId}")
    public ResponseEntity<?> deleteTeam(@PathVariable(name = "teamId") int teamId) {
        adminService.deleteTeam(teamId);
        return response.handleSuccess("팀 삭제 완료");
    }

    // 팀 전체 조회
    @GetMapping("/")
    public ResponseEntity<?> getTeamAll() {return response.handleSuccess(adminService.getTeamAll()); }

    // 팀 세부 조회
    @GetMapping("/{teamId}")
    public ResponseEntity<?> getTeam(@PathVariable(name = "teamId") int teamId) {
        return response.handleSuccess(adminService.getTeam(teamId));
    }
}
