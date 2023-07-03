package controller;

import com.google.gson.Gson;
import model.UserDatabase;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class NotificationController {
    private static String username = null;
    private static long startTime;
    private static int numberOfNotifications = 0;
    public void handle(Socket client) {
        startTime = System.currentTimeMillis();
        while (true) {
            try {
                System.out.println(username + " is online");
                DataInputStream dis = new DataInputStream(client.getInputStream());
                String string = dis.readUTF();
                numberOfNotifications++;
                String[] inputSplit = new Gson().fromJson(string, String[].class);
                if (username == null) {
                    username = inputSplit[0];
                }
            } catch (IOException e) {
                System.out.println(username + " is offline");
                if (username != null) {
                    UserDatabase.getUserByUsername(username).setOnline(false);
                    break;
                }
            }
            if ((System.currentTimeMillis() - startTime) / 10000 > numberOfNotifications ) {
                if (username != null) {
                    UserDatabase.getUserByUsername(username).setOnline(false);
                    break;
                }
            }
        }
    }
}
