package controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ProfileThread extends Thread {
    public void run() {
        try {
            ProfileController profileController = new ProfileController();
            ServerSocket ss = new ServerSocket(8002);
            while (true) {
                Socket client = ss.accept();
                profileController.handle(client);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
