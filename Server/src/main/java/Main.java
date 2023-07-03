import controller.*;
import model.UserDatabase;

public class Main {
    public static void main(String[] args) {
        UserDatabase.loadUsers();
        RegisterThread registerThread = new RegisterThread();
        registerThread.start();
        LoginThread loginThread = new LoginThread();
        loginThread.start();
        ProfileThread profileThread = new ProfileThread();
        profileThread.start();
        ChatThread chatThread = new ChatThread();
        chatThread.start();
        LobbyThread lobbyThread = new LobbyThread();
        lobbyThread.start();
    }
}