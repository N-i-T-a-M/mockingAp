package controller;

import java.net.ServerSocket;
import java.net.Socket;

public class LobbyThread extends Thread {
    @Override
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(5000);
            LobbyController lobbyController = new LobbyController();
            while (true) {
                Socket client = ss.accept();
                lobbyController.handle(client);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
