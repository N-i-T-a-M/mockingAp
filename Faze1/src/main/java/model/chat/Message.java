package model.chat;

import model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Message {
    private User sender;
    private ArrayList<User> receivers;
    private Chat chat;
    private String content;

    public User getSender() {
        return sender;
    }

    public ArrayList<User> getReceivers() {
        return receivers;
    }

    public Chat getChat() {
        return chat;
    }

    public String getContent() {
        return content;
    }

    public String getSendingTime() {
        return sendingTime;
    }

    private String sendingTime;
    private boolean isSeen = false;
    private String senderAvatar;
    private char isSeenChar = ' ';

    public void setIsSeenChar(char isSeenChar) {
        this.isSeenChar = isSeenChar;
    }

    public char getIsSeenChar() {
        return isSeenChar;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSenderAvatar() {
        return senderAvatar;
    }

    private ArrayList<Reaction> reactions = new ArrayList<>();
    {
        Reaction like = new Reaction("👍",this,0);
        Reaction dislike = new Reaction("👎",this,0);
        Reaction heart = new Reaction("❤",this,0);
        Reaction heartEyes = new Reaction("😍",this,0);
        Reaction laugh = new Reaction("😂",this,0);
        reactions.add(like);
        reactions.add(dislike);
        reactions.add(heart);
        reactions.add(heartEyes);
        reactions.add(laugh);
    }
    public Message(User sender, Chat chat, String content) {
        this.sender = sender;
        this.chat = chat;
        this.content = content;
        this.receivers = chat.getUsers();
        receivers.remove(sender);
        String time = getTime();
        this.sendingTime = time;
        chat.getMessages().add(this);
        this.senderAvatar = sender.getAvatar();
        Pair pair = new Pair(sender, this);
        for (User receiver : receivers) {
            pair = new Pair(receiver, this);
        }
    }

    private static String getTime() {
        LocalDateTime now = LocalDateTime.now();
        int hour = now.getHour();
        int minute = now.getMinute();
        String time = "";
        if (hour < 10) {
            time += "0" + hour + ":";
        } else {
            time += hour + ":";
        }
        if (minute < 10) {
            time += "0" + minute;
        } else {
            time += minute;
        }
        return time;
    }

    public ArrayList<Reaction> getReactions() {
        return reactions;
    }
}
