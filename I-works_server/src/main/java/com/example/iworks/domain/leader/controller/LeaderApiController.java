package com.example.iworks.domain.leader.controller;

import com.example.iworks.domain.leader.service.LeaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/leader")
@RequiredArgsConstructor
public class LeaderApiController {

    private final LeaderService leaderService;

    @DeleteMapping("/board/{boardId}")
    public ResponseEntity<Map<String,Object>> deleteBoard(@PathVariable int boardId, @RequestHeader("Authorization") String token){
        return leaderService.deleteBoard(boardId,token);
    }
}
