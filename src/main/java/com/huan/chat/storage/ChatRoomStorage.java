package com.huan.chat.storage;

import com.huan.chat.domain.ChatRoom;

import java.util.List;

/**
 * 聊天室服务
 */
public interface ChatRoomStorage {
    boolean createChatRoom(String name);

    boolean deleteChatRoom(String name);

    List<ChatRoom> queryChatRooms();

    ChatRoom queryChatRoomsByIdOrName(String idOrName);
}
