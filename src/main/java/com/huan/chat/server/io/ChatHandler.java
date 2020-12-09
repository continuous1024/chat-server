package com.huan.chat.server.io;

import com.huan.chat.domain.ChatRoom;
import com.huan.chat.domain.User;
import com.huan.chat.storage.ChatRoomStorage;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

@AllArgsConstructor
public class ChatHandler implements Runnable {
    private User user;

    private ChatRoomStorage chatRoomStorage;

    @Override
    public void run() {
        try (InputStream inStream = user.getSocket().getInputStream();
             OutputStream outputStream = user.getSocket().getOutputStream();
             Scanner in = new Scanner(inStream);
             PrintWriter out = new PrintWriter(outputStream, true);
        ) {
            List<ChatRoom> rooms = chatRoomStorage.queryChatRooms();
            out.println("聊天室列表如下: ");
            out.println("聊天室ID，   聊天室名称");
            for (ChatRoom chatRoom : rooms) {
                out.println(chatRoom.getId() + "  " +chatRoom.getName());
            }

            out.println("请输入聊天室名称或者ID加入聊天室：");

            int errCount = 0;
            ChatRoom room = null;
            while (in.hasNextLine()) {
                String line = in.nextLine();
                room = chatRoomStorage.queryChatRoomsByIdOrName(line);
                if (room == null) {
                    errCount ++;
                    if (errCount > 3) {
                        out.println("超过失败次数");
                        return;
                    }
                    out.println("聊天室名称或者ID输入有误，请重新输入");
                    continue;
                }
                break;
            }

            assert room != null;
            boolean joinResult = room.join(user);
            if (joinResult) {
                out.println("欢迎加入" + room.getName() +"聊天室");
            }

            out.println(room.getUsers());

            for (User user : room.getUsers()) {
                OutputStream userOutputStream = user.getSocket().getOutputStream();
                PrintWriter userOut = new PrintWriter(outputStream, true);
                userOut.println("大家好，请多多关照");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
