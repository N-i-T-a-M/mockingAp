package model.chat;

import model.User;

import java.util.ArrayList;

public class Pair {
    private User user;
    private Message message;

    public static ArrayList<Pair> getPairs() {
        return pairs;
    }

    private static ArrayList<Pair> pairs = new ArrayList<>();
    public Pair(User user, Message message) {
        this.user = user;
        this.message = message;
        pairs.add(this);
    }

    public User getUser() {
        return user;
    }

    public Message getMessage() {
        return message;
    }
    private boolean isUserReacted = false;

    public boolean isUserReacted() {
        return isUserReacted;
    }

    public void setUserReacted(boolean userReacted) {
        isUserReacted = userReacted;
    }
}
