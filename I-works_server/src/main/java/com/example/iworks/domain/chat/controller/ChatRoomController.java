package com.example.iworks.domain.chat.controller;

import com.example.iworks.domain.chat.repository.ChatRoomRepository;
import com.example.iworks.global.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/chat/room")
public class ChatRoomController {

    private final ChatRoomRepository chatRoomRepository;
    private final Response response;

    //채팅방 전체 조회
    @GetMapping
    public ResponseEntity<?> getRooms() {
        return response.handleSuccess(chatRoomRepository.findAll());
    }

    //채팅방 상세 조회
    @GetMapping("/{chatRoomId}")
    public ResponseEntity<?> gerRoom(@PathVariable(name = "chatRoomId") String chatRoomId) {
        return response.handleSuccess(chatRoomRepository.findChatRoomById(chatRoomId));
    }
    
    //채팅방 생성
    @PostMapping
    public ResponseEntity<?> createRoom(@RequestParam(name = "chatRoomName") String chatRoomName) {
        return response.handleSuccess(chatRoomRepository.createChatRoom(chatRoomName));
    }

    //채팅방 입장
    @GetMapping("/enter/{chatRoomId}")
    public ResponseEntity<?> viewRoom(@PathVariable(name = "chatRoomId") String chatRoomId) {
        Map<String, String> result = new HashMap<>();
        result.put("roomId", chatRoomId);
        return response.handleSuccess(result);
    }

    @GetMapping("/view")
    public String viewRooms() {
        return "/chat/room";
    }

    //채팅방 입장
    @GetMapping("/view/enter/{chatRoomId}")
    public String viewRoom(Model model, @PathVariable(name = "chatRoomId") String chatRoomId) {
        model.addAttribute("roomId", chatRoomId);
        return "/chat/roomdetail";
    }

}