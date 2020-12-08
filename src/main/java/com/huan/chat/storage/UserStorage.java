package com.huan.chat.storage;

import com.huan.chat.domain.User;

import java.io.Writer;

/**
 * 用户服务
 */
public interface UserStorage {
    User login(String username, String password, Writer writer);

    User register(String username, String password, Writer writer);
}
