package Commands;

import com.google.gson.Gson;
import model.User;
import view.MainMenu;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Notification extends Thread {
    private static final int PORT = 1234;
    private static User currentUser;
    @Override
    public void run() {
        currentUser = MainMenu.getC
        try {
            Socket socket = new Socket("localhost", PORT); // replace with your server information
            OutputStream out = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(out, true);
            writer.println("Notification message");
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            String [] json = {new Gson().toJson()}
            // close resources
            writer.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
