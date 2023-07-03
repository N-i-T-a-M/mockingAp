package controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class StartThread extends Thread {
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(6000);
            StartController startController = new StartController();
            while (true) {
                Socket client = ss.accept();
                startController.handle(client);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
