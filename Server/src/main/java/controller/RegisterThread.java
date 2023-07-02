package controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RegisterThread extends Thread {
    public void run() {
        try {
            RegisterController registerController = new RegisterController();
            ServerSocket ss = new ServerSocket(8000);
            while (true) {
                Socket client = ss.accept();
                registerController.handle(client);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
