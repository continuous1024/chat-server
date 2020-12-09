package com.huan.chat.storage;

import com.huan.chat.domain.ChatRoom;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomStorageImpl implements ChatRoomStorage {
    private List<ChatRoom> rooms = new ArrayList<>();

    @Override
    public boolean createChatRoom(String name) {
        // check is or not exist
        ChatRoom chatRoom = new ChatRoom(name);
        return rooms.add(chatRoom);
    }

    @Override
    public boolean deleteChatRoom(String id) {
        return rooms.removeIf(chatRoom -> id.equals(chatRoom.getId()));
    }

    @Override
    public List<ChatRoom> queryChatRooms() {
        return rooms;
    }

    @Override
    public ChatRoom queryChatRoomsByIdOrName(String idOrName) {
        return rooms.stream()
                .filter(chatRoom -> idOrName.equals(chatRoom.getId()) || idOrName.equals(chatRoom.getName()))
                .findFirst().orElseGet(() -> null);
    }
}
