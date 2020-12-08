package com.huan.chat.server.io;

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
    public final String END_OF_MESSAGE = "$Bye";
    public final String LOGIN_MESSAGE = "$Login";
    public final String Register_OF_MESSAGE = "$Register";

    private Socket socket;

    private UserStorage userStorage;

    @Override
    public void run() {
        try (InputStream inStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            Scanner in = new Scanner(inStream);
            PrintWriter out = new PrintWriter(outputStream, true);
        ) {
            out.println("Welcome! Please Input $Login to login, Input $Register to register, Input $Bye to Bye bye");
            boolean isEnd = loginOrRegister(in, out);
            // 如果用户结束，那就结束吧
            if (isEnd) {
                return;
            }

            while (in.hasNextLine()) {
                String line = in.nextLine();
                if (line.equals(END_OF_MESSAGE)) {
                    out.println("Bye Bye");
                    break;
                } else {
                    // 单例 or 依赖注入
                    ChatRoomStorage chatRoomStorage = new ChatRoomStorageImpl();
                    out.println("Ha Ha, 欢迎来到换宇聊天室");
                    new Thread(new ChatHandler(socket, chatRoomStorage)).start();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean loginOrRegister(Scanner in, PrintWriter out) {
        boolean isEnd = false;
        label:
        while (in.hasNextLine()) {
            String line = in.nextLine();
            switch (line) {
                case LOGIN_MESSAGE: {
                    User user = login(in, out);
                    if (user != null) {
                        out.println("Welcome " + user.getUsername() + " to login");
                        break label;
                    }
                    break;
                }
                case END_OF_MESSAGE:
                    out.println("Bye Bye");
                    isEnd = true;
                    break label;
                case Register_OF_MESSAGE: {
                    User user = register(in, out);
                    if (user != null) {
                        break label;
                    }
                    break;
                }
                default:
                    out.println("Please Input $Login to login, Input $Register to register, Input $Bye to Bye bye");
                    break;
            }
        }
        return isEnd;
    }

    private User login(Scanner in, PrintWriter out) {
        out.println("Please input username and password to login");
        String username = in.nextLine();
        out.println("You username is " + username);
        String password = in.nextLine();
        out.println("You password is " + password);
        return userStorage.login(username, password, out);
    }

    private User register(Scanner in, PrintWriter out) {
        out.println("Please input username and password to Register");
        String username = in.nextLine();
        out.println("You username is " + username);
        String password = in.nextLine();
        out.println("You password is " + password);
        return userStorage.register(username, password, out);
    }
}
