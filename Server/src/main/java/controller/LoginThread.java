package controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class LoginThread extends Thread {
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(3000);
            LoginController loginController = new LoginController();
            while (true) {
                Socket client = ss.accept();
                loginController.handle(client);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
