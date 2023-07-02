package controller;

import com.google.gson.Gson;
import model.User;
import model.chat.Chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatMenuController {
    private static final int PORT = 7100;

    public static void giveTheChatToUser(Chat chat, User user) {
        if (!user.getChats().contains(chat)) {
            user.getChats().add(chat);
        }
        if (!chat.getUsers().contains(user)) {
            chat.getUsers().add(user);
        }
        try {
            Socket socket = new Socket("localhost", PORT);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            String[] json = {"giveTheChatToUser", new Gson().toJson(chat), user.getUsername()};
            dos.writeUTF(new Gson().toJson(json));
            dos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static String addUserToChat(String toAdd, Chat currentChat) {
        if (toAdd.equals(currentChat.getAdmin().getUsername())) {
            return "userAlreadyInChat";
        }
        try {
            Socket socket = new Socket("localhost", PORT);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            String[] json = {"addUserToChat", toAdd, new Gson().toJson(currentChat)};
            dos.writeUTF(new Gson().toJson(json));
            dos.flush();
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            if (dis.readUTF().equals("userNotFound")) {
                return "userNotFound";
            }
            if (dis.readUTF().equals("userAlreadyInChat")) {
                return "userAlreadyInChat";
            }
            User user = new Gson().fromJson(dis.readUTF(), User.class);
            if (!currentChat.getUsers().contains(user)) {
                currentChat.getUsers().add(user);
            }
            if (!user.getChats().contains(currentChat)) {
                user.getChats().add(currentChat);
            }
            return "userAdded";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveChats() {
        try {
            Socket socket = new Socket("localhost", PORT);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            String[] json = {"saveChats", new Gson().toJson(Chat.getChats())};
            dos.writeUTF(new Gson().toJson(json));
            dos.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadChats() {
        try {
            Socket socket = new Socket("localhost", PORT);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            String[] json = {"loadChats"};
            dos.writeUTF(new Gson().toJson(json));
            dos.flush();
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            Chat.getChats().clear();
            while (true) {
                String input = dis.readUTF();
                if (input.equals("finished")) {
                    break;
                }
                Chat.getChats().add(new Gson().fromJson(input, Chat.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
