package com.example.iworks.domain.chat.dto;

import com.example.iworks.domain.chat.entity.ChatMessageType;
import lombok.Data;

@Data
public class ChatMessage {

    private String chatRoomId;              //채팅방 아이디
    private ChatMessageType chatMessageType;           //채팅 메시지 타입
    private String chatMessageSenderName;   //채팅 전송사 이름
    private String chatMessageContent;      //채팅 메시지 내용

}
