package com.huan.chat.storage;

import com.huan.chat.domain.ChatRoom;

import java.util.List;

/**
 * 聊天室服务
 */
public interface ChatRoomStorage {
    public boolean createChatRoom(String name);

    public boolean deleteChatRoom(String name);

    public List<ChatRoom> queryChatRooms();
}
