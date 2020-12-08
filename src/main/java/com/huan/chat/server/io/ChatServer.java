package com.huan.chat.server.io;

import com.huan.chat.storage.ChatRoomStorage;
import com.huan.chat.storage.UserStorage;
import com.huan.chat.storage.UserStorageImpl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

    private UserStorage userStorage;
    private ChatRoomStorage chatRoomStorage;

    public void run(int port) {
        userStorage = new UserStorageImpl();
        // init chat room
        try(ServerSocket server = new ServerSocket(port)) {
            while (true) {
                Socket socket = server.accept();
                System.out.println("接收到用户请求");
                new Thread(new UserHandler(socket, userStorage)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ChatServer().run(4000);
    }
}
