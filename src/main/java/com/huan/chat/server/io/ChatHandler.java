package com.huan.chat.server.io;

import com.huan.chat.storage.ChatRoomStorage;
import lombok.AllArgsConstructor;

import java.net.Socket;

@AllArgsConstructor
public class ChatHandler implements Runnable {
    private Socket socket;

    private ChatRoomStorage chatRoomStorage;

    @Override
    public void run() {

    }
}
