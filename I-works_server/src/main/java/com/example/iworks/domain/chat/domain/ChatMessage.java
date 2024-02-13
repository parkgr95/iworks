package com.example.iworks.domain.chat.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ChatMessage {

    private MessageType type;    //채팅 메시지 타입
    private String roomId;                  //채팅방
    private String sender;       //채팅 전송사 이름
    private String message;          //채팅 메시지 내용

}
