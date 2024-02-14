package com.example.iworks.domain.chat.controller;

import com.example.iworks.domain.chat.domain.ChatMessage;
import com.example.iworks.domain.chat.domain.MessageType;
import com.example.iworks.domain.chat.pubsub.RedisPublisher;
import com.example.iworks.domain.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ChatMessageController {

    private final RedisPublisher redisPublisher;
    private final ChatRoomRepository chatRoomRepository;

    @MessageMapping("/chat/message")
    public void message(ChatMessage message) {
        if (message.getType().equals(MessageType.ENTER)) {
            chatRoomRepository.enterChatRoom(message.getRoomId());
            message.setMessage(message.getSender() + "님이 입장하셨습니다.");
        }
        redisPublisher.publish(chatRoomRepository.getTopic(message.getRoomId()), message);
    }

}
