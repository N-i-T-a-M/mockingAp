package controller;

import com.google.gson.Gson;
import model.UserDatabase;
import model.chat.Chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ChatController {
    public synchronized void handle(Socket client) {
        System.out.println("accepted chat socket");
        try {
            DataInputStream dis = new DataInputStream(client.getInputStream());
            String message = dis.readUTF();
            System.out.println(message);
            String[] inputSplit = new Gson().fromJson(message, String[].class);
            if (inputSplit[0].equals("giveTheChatToUser")) {
                String username = inputSplit[2];
                Chat chat = new Gson().fromJson(inputSplit[1], Chat.class);
                if (!chat.getUsers().contains(UserDatabase.getUserByUsername(username))) {
                    chat.getUsers().add(UserDatabase.getUserByUsername(username));
                }
                if (!UserDatabase.getUserByUsername(username).getChats().contains(chat)) {
                    UserDatabase.getUserByUsername(username).getChats().add(chat);
                }
            } else if (inputSplit[0].equals("addUserToChat")) {
                String username = inputSplit[1];
                Chat chat = new Gson().fromJson(inputSplit[2], Chat.class);
                DataOutputStream dos = new DataOutputStream(client.getOutputStream());
                if (UserDatabase.getUserByUsername(username) == null) {
                    dos.writeUTF("userNotFound");
                    dos.flush();
                } else {
                    if (chat.getUsers().contains(UserDatabase.getUserByUsername(username))) {
                        dos.writeUTF("userAlreadyInChat");
                        dos.flush();
                    } else if (!UserDatabase.getUserByUsername(username).getChats().contains(chat)) {
                        UserDatabase.getUserByUsername(username).getChats().add(chat);
                        dos.writeUTF(new Gson().toJson(UserDatabase.getUserByUsername(username)));
                        dos.flush();
                    }
                    if (!chat.getUsers().contains(UserDatabase.getUserByUsername(username))) {
                        chat.getUsers().add(UserDatabase.getUserByUsername(username));
                    }
                }
            } else if (inputSplit[0].equals("saveChats")) {
                ArrayList<Chat> chats = new Gson().fromJson(inputSplit[1], ArrayList.class);
                Chat.getChats().clear();
                Chat.getChats().addAll(chats);
                Chat.saveChats();
            } else if (inputSplit[0].equals("loadChats")) {
                Chat.loadChats();
                DataOutputStream dos = new DataOutputStream(client.getOutputStream());
                for (Chat chat : Chat.getChats()) {
                    dos.writeUTF(new Gson().toJson(chat));
                }
                dos.writeUTF("finished");
                dos.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
