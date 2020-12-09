package com.huan.chat.server.io;

import com.huan.chat.constants.Constants;
import com.huan.chat.domain.User;
import com.huan.chat.storage.ChatRoomStorage;
import com.huan.chat.storage.ChatRoomStorageImpl;
import com.huan.chat.storage.UserStorage;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

@AllArgsConstructor
public class UserHandler implements Runnable {

    private Socket socket;

    private UserStorage userStorage;

    private ChatRoomStorage chatRoomStorage;

    @Override
    public void run() {
        try {
            InputStream inStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            Scanner in = new Scanner(inStream);
            PrintWriter out = new PrintWriter(outputStream, true);
            out.println("Welcome! Please Input $Login to login, Input $Register to register, Input $Bye to Bye bye");
            User user = loginOrRegister(in, out);
            // 如果用户结束，那就结束吧
            if (user == null) {
                return;
            }

            out.println("Ha Ha, 欢迎来到换宇聊天室");
            new Thread(new ChatHandler(user, chatRoomStorage)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private User loginOrRegister(Scanner in, PrintWriter out) {
        User user;
        while (in.hasNextLine()) {
            String line = in.nextLine();
            switch (line) {
                case Constants.LOGIN_MESSAGE: {
                    user = login(in, out);
                    if (user != null) {
                        out.println("Welcome " + user.getUsername() + " to login");
                        return user;
                    }
                    break;
                }
                case Constants.END_OF_MESSAGE:
                    out.println("Bye Bye");
                    return null;
                case Constants.Register_OF_MESSAGE: {
                    user = register(in, out);
                    if (user != null) {
                        return user;
                    }
                    break;
                }
                default:
                    out.println("Please Input $Login to login, Input $Register to register, Input $Bye to Bye bye");
                    break;
            }
        }
        return null;
    }

    private User login(Scanner in, PrintWriter out) {
        out.println("Please input username and password to login");
        String username = in.nextLine();
        out.println("You username is " + username);
        String password = in.nextLine();
        out.println("You password is " + password);
        return userStorage.login(username, password, socket);
    }

    private User register(Scanner in, PrintWriter out) {
        out.println("Please input username and password to Register");
        String username = in.nextLine();
        out.println("You username is " + username);
        String password = in.nextLine();
        out.println("You password is " + password);
        return userStorage.register(username, password, socket);
    }
}
