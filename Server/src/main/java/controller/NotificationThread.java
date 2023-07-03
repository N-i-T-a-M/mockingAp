package controller;

import model.User;
import model.UserDatabase;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class NotificationThread extends Thread {

    public void run() {
        try {

            ServerSocket serverSocket = new ServerSocket(1234);
            NotificationController controller = new NotificationController();
            while (true) {
                Socket client = serverSocket.accept();
                controller.handle(client);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
