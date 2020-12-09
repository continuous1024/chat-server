package com.huan.chat.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Writer;
import java.net.Socket;

@Data
@AllArgsConstructor
public class User {
    private String username;
    private String password;
    private Socket socket;
}
