package model;

import com.google.gson.Gson;
import model.chat.Chat;

import java.io.Serializable;
import java.util.ArrayList;

public class GameRequest implements Serializable {
    private int capacity;
    private ArrayList<User> players = new ArrayList<>();
    private User admin;
    private Long Id;
    private boolean isPublic = true;
    private long timeOfLastEntry;
    private Chat chat;
    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public long getTimeOfLastEntry() {
        return timeOfLastEntry;
    }

    public void setTimeOfLastEntry(long timeOfLastEntry) {
        this.timeOfLastEntry = timeOfLastEntry;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public int getCapacity() {
        return capacity;
    }

    public ArrayList<User> getPlayers() {
        return players;
    }

    public User getAdmin() {
        return admin;
    }

    public Long getId() {
        return Id;
    }

    public Chat getChat() {
        return chat;
    }

    public GameRequest(int capacity, User admin) {
        this.capacity = capacity;
        this.admin = admin;
        this.Id = System.currentTimeMillis();
        this.timeOfLastEntry = System.currentTimeMillis();
        this.chat = new Chat(admin,this.players,getId().toString());
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}
