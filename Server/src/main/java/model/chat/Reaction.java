package model.chat;

import model.User;

public class Reaction {
    private String reaction;
    private Message message;
    private int numberOfReactions;
    private User user;

    public String getReaction() {
        return reaction;
    }

    public void setReaction(String reaction) {
        this.reaction = reaction;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public int getNumberOfReactions() {
        return numberOfReactions;
    }

    public void setNumberOfReactions(int numberOfReactions) {
        this.numberOfReactions = numberOfReactions;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Reaction(String reaction, Message message, int numberOfReactions) {
        this.reaction = reaction;
        this.message = message;
        this.numberOfReactions = numberOfReactions;
    }
}
