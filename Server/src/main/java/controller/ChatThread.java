package controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatThread extends Thread{
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(7100);
            ChatController chatController = new ChatController();
            while (true) {
                Socket client = ss.accept();
                chatController.handle(client);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
