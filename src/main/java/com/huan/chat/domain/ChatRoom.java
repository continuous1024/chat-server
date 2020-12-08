package com.huan.chat.domain;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ChatRoom {
    private String id;

    private String name;

    private List<User> users;

    private int limit;

    public ChatRoom(String name, int limit) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        // may be init users from database or redis
        this.limit = limit;
    }

    public ChatRoom(String name) {
        this(name, 10);
    }

    public boolean join(User user) {
        if (users.size() < limit) {
            users.add(user);
            return true;
        }
        return false;
    }

    public boolean leave(User user) {
        // may be not
        users.remove(user);
        return true;
    }

    public List<User> queryUsers() {
        return users;
    }
}
