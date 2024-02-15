package com.example.iworks.domain.chat.repository;

import com.example.iworks.domain.chat.dto.ChatRoom;
import com.example.iworks.domain.chat.service.RedisSubscriber;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Repository;

import java.util.*;

@RequiredArgsConstructor
@Repository
public class ChatRoomRepository {

    private static final String CHAT_ROOM = "CHAT_ROOM";

    private final RedisMessageListenerContainer redisMessageListener;
    private final RedisSubscriber redisSubscriber;
    private final RedisTemplate<String, Object> redisTemplate;

    private HashOperations<String, String, ChatRoom> opsHashChatRoom;
    private Map<String, ChannelTopic> topics;

    @PostConstruct
    private void init() {
        opsHashChatRoom = redisTemplate.opsForHash();
        topics = new HashMap<>();
    }

    public List<ChatRoom> findAll() {
        return opsHashChatRoom.values(CHAT_ROOM);
    }

    public ChatRoom findChatRoomById(String chatRoomId) {
        return opsHashChatRoom.get(CHAT_ROOM, chatRoomId);
    }

    public ChatRoom createChatRoom(String chatRoomName) {
        ChatRoom chatRoom = ChatRoom.create(chatRoomName);
        opsHashChatRoom.put(CHAT_ROOM, chatRoom.getChatRoomId(), chatRoom);
        return chatRoom;
    }

    public void enterChatRoom(String chatRoomId) {
        topics.computeIfAbsent(chatRoomId, id -> {
            ChannelTopic newTopic = new ChannelTopic(id);
            redisMessageListener.addMessageListener(redisSubscriber, newTopic);
            return newTopic;
        });
    }

    public ChannelTopic getTopic(String chatRoomId) {
        return topics.get(chatRoomId);
    }

}