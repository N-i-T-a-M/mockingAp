package controller;

import com.google.gson.Gson;
import model.User;
import view.MainMenu;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Notification extends Thread {
    private static final int PORT = 1234;
    private static User currentUser;
    private static boolean isWindowClosed = false;
    public static void setWindowClosed(boolean b) {

    }

    @Override
    public void run() {
        try {
            currentUser = MainMenu.getCurrentUser();
            System.out.println("starting thread");
            Socket socket = new Socket("localhost", PORT); // replace with your server information
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            String[] json = {currentUser.getUsername()};
            String send = new Gson().toJson(json);
            while (true) {
                dos.writeUTF(send);
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (MainMenu.getCurrentUser() != null) {
                    currentUser = MainMenu.getCurrentUser();
                } else {
                    break;
                }
                if (isWindowClosed) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
