package controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatThread extends Thread{
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(8080);
            while (true) {
                Socket client = ss.accept();

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
