package com.example.iworks.domain.chat.controller;

import com.example.iworks.domain.chat.dto.ChatMessage;
import com.example.iworks.domain.chat.entity.ChatMessageType;
import com.example.iworks.domain.chat.service.RedisPublisher;
import com.example.iworks.domain.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ChatMessageController {

    private final RedisPublisher redisPublisher;
    private final ChatRoomRepository chatRoomRepository;

    @MessageMapping("/chat/message")
    public void message(ChatMessage message) {
        if (message.getChatMessageType().equals(ChatMessageType.ENTER)) {
            chatRoomRepository.enterChatRoom(message.getChatRoomId());
            message.setChatMessageContent(message.getChatMessageSenderName() + "님이 입장하셨습니다.");
        }
        redisPublisher.publish(chatRoomRepository.getTopic(message.getChatRoomId()), message);
    }

}

