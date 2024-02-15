package com.example.iworks.domain.chat.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
public class ChatRoom implements Serializable {

    private static final long serialVersionUID = 6494678977089006639L;

    private String chatRoomId;
    private String chatRoomName;

    public static ChatRoom create(String chatRoomName) {
        return ChatRoom.builder()
                .chatRoomId(UUID.randomUUID().toString())
                .chatRoomName(chatRoomName)
                .build();
    }

}
