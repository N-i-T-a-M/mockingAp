package model.chat;

import model.User;

import java.util.ArrayList;

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
        chatType = ChatType.PRIVATE;
        this.admin = admin;
        this.users.add(admin);
        this.users.add(user);
        this.name = name;
        chats.add(this);
        this.admin.getChats().add(this);
        user.getChats().add(this);
    }

    public static ArrayList<Chat> getChats() {
        return chats;
    }

    public Chat(User admin, ArrayList<User> users, String name) {
        chatType = ChatType.ROOM;
        this.admin = admin;
        this.users = users;
        this.users.add(admin);
        this.name = name;
        chats.add(this);
//        this.admin.getChats().add(this);
//        for (User user : users) {
//            user.getChats().add(this);
//        }
//        UserDatabase.getRoomChats().add(this);
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
