package controller;

import com.google.gson.Gson;
import model.User;
import model.UserDatabase;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ProfileController {
    public void handle(Socket client) {
        System.out.println("ProfileController.handle");
        DataInputStream dis = null;
        try {
            dis = new DataInputStream(client.getInputStream());
        String input = dis.readUTF();
            String[] inputSplit = (new Gson()).fromJson(input, String[].class);
            for (int i = 0; i < inputSplit.length; i++) {
                System.out.print(inputSplit[i] + " ");
            }
            if (inputSplit[0].equals("addFromFriendList")) {
                User user1 = UserDatabase.getUserByUsername(inputSplit[1]);
                User user2 = UserDatabase.getUserByUsername(inputSplit[2]);
                user2.getFriends().add(user1);
                user2.getWaitingForYouToAccept().remove(user1);
                user1.getFriends().add(user2);
                user1.getWaitingForThemToAccept().remove(user2);
                //todo send them some message
            }
            else if (inputSplit[0].equals("removeFromFriendList")) {
                User user1 = UserDatabase.getUserByUsername(inputSplit[1]);
                User user2 = UserDatabase.getUserByUsername(inputSplit[2]);
                user2.getWaitingForYouToAccept().remove(user1);
                user1.getWaitingForThemToAccept().remove(user2);
                //todo send them some message
            }
            else if (inputSplit[0].equals("sendFriendRequest")) {
                User user1 = UserDatabase.getUserByUsername(inputSplit[1]);
                User user2 = UserDatabase.getUserByUsername(inputSplit[2]);
                user1.getWaitingForYouToAccept().add(user2);
                user2.getWaitingForThemToAccept().add(user1);
            }
            else if (inputSplit[0].equals("rankPlayers")) {
                ArrayList<User> users = UserDatabase.rankPlayers();
                String output = (new Gson()).toJson(users);
                DataOutputStream write = new DataOutputStream(client.getOutputStream());
                write.writeUTF(output);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
