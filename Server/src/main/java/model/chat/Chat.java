package model.chat;

import com.google.gson.Gson;
import model.User;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Chat {
    private ChatType chatType;
    private ArrayList<Message> messages = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();
    private User admin;
    private String name;
    private static ArrayList<Chat> chats = new ArrayList<>();

    public User getAdmin() {
        return admin;
    }

    public String getName() {
        return name;
    }

    public Chat(User admin, User user, String name) {
        loadChats();
        chatType = ChatType.PRIVATE;
        this.admin = admin;
        this.users.add(admin);
        this.users.add(user);
        this.name = name;
        chats.add(this);
        saveChats();
    }

    public static ArrayList<Chat> getChats() {
        return chats;
    }

    public Chat(User admin, ArrayList<User> users, String name) {
        loadChats();
        chatType = ChatType.ROOM;
        this.admin = admin;
        this.users = users;
        this.users.add(admin);
        this.name = name;
        chats.add(this);
        saveChats();
    }
    public static void saveChats() {
        String json = gson.toJson(chats.toArray());
        File file = new File("chats.json");
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(json);
            fileWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static Gson gson = new Gson();
    public static void loadChats() {
        try {

            File file = new File("chats.json");
            if (file.exists() && file.length() != 0) {
                Scanner scanner = new Scanner(file);
                String fileContent = scanner.useDelimiter("\\Z").next();
                scanner.close();
                Chat[] chats = gson.fromJson(fileContent, Chat[].class);
                Chat.chats = new ArrayList<>();
                Collections.addAll(Chat.chats, chats);
            } else {
                chats = new ArrayList<>();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void setChatType(ChatType chatType) {
        this.chatType = chatType;
    }

    public ChatType getChatType() {
        return chatType;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public String addUser(User user) {
        if (!users.contains(user)) {
            if (chatType != ChatType.PRIVATE) {
                users.add(user);
//                user.getChats().add(this);
                return "user added successfully";
            }
            return "you can't add users to a private chat";
        }
        return "user already in the chat";
    }
}
