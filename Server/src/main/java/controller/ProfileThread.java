package controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ProfileThread extends Thread {
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(8100);
            ProfileController profileController = new ProfileController();
            while (true) {
                Socket client = ss.accept();
                System.out.println("accepted profile socket");
                profileController.handle(client);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
