package com.huan.chat.storage;

import com.huan.chat.domain.User;

import java.io.Writer;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class UserStorageImpl implements UserStorage {
    private static final Map<String, User> userMap = new HashMap<>();

    @Override
    public User login(String username, String password, Socket socket) {
        User user = userMap.get(username);
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("密码不正确");
        }
        user.setSocket(socket);
        return user;
    }

    @Override
    public User register(String username, String password, Socket socket) {
        User user = userMap.get(username);
        if (user != null) {
            throw new RuntimeException("用户名已存在");
        }
        user = new User(username, password, socket);
        userMap.put(username, user);
        return user;
    }
}
