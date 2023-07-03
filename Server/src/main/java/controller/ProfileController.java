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
                assert user2 != null;
                user2.getFriends().add(user1);
                user2.getWaitingForYouToAccept().remove(user1);
                assert user1 != null;
                user1.getFriends().add(user2);
                user1.getWaitingForThemToAccept().remove(user2);
                //todo send them some message
            } else if (inputSplit[0].equals("removeFromFriendList")) {
                User user1 = UserDatabase.getUserByUsername(inputSplit[1]);
                User user2 = UserDatabase.getUserByUsername(inputSplit[2]);
                assert user2 != null;
                user2.getWaitingForYouToAccept().remove(user1);
                assert user1 != null;
                user1.getWaitingForThemToAccept().remove(user2);
                //todo send them some message
            } else if (inputSplit[0].equals("sendFriendRequest")) {
                User user1 = UserDatabase.getUserByUsername(inputSplit[1]);
                User user2 = UserDatabase.getUserByUsername(inputSplit[2]);
                assert user1 != null;
                user1.getWaitingForYouToAccept().add(user2);
                assert user2 != null;
                user2.getWaitingForThemToAccept().add(user1);
            } else if (inputSplit[0].equals("rankPlayers")) {
                ArrayList<User> users = UserDatabase.rankPlayers();
                DataOutputStream write = new DataOutputStream(client.getOutputStream());
                for (User user : users) {
                    write.writeUTF((new Gson()).toJson(user));
                }
                write.writeUTF("finished");
                write.flush();
            }
            else if (inputSplit[0].equals("setUserOnline")) {//todo : check
                String username = inputSplit[1];
                User user = UserDatabase.getUserByUsername(username);
                assert user != null;
                user.setOnline(true);
            }
            else if (inputSplit[0].equals("update")) {
                UserDatabase.updateUser(new Gson().fromJson(inputSplit[1], User.class));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
