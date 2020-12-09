package com.huan.chat.storage;

import com.huan.chat.domain.User;

import java.io.Writer;
import java.net.Socket;

/**
 * 用户服务
 */
public interface UserStorage {
    User login(String username, String password, Socket socket);

    User register(String username, String password, Socket socket);
}
