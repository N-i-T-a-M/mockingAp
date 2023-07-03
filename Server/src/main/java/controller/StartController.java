package controller;

import com.google.gson.Gson;
import model.GameRequest;
import model.User;
import model.UserDatabase;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class StartController {
    public void handle(Socket client) {
        try {
            DataInputStream dis = new DataInputStream(client.getInputStream());
            String message = dis.readUTF();
            String[] inputSplit = new Gson().fromJson(message, String[].class);
            if (inputSplit[0].equals("removePlayer")) {
                String username = inputSplit[1];
                DataOutputStream dos = new DataOutputStream(client.getOutputStream());
                for (User user : UserDatabase.getPlayers()) {
                    if (user.getUsername().equals(username)) {
                        UserDatabase.getPlayers().remove(user);
                        String[] outputSplit = {"Player removed successfully"};
                        dos.writeUTF(new Gson().toJson(outputSplit));
                        dos.flush();
                    }
                }
                dos.writeUTF("user is not in the list");
            } else if (inputSplit[0].equals("removeAllPlayers")) {
                DataOutputStream dos = new DataOutputStream(client.getOutputStream());
                if (UserDatabase.getPlayers().size() == 0) {
                    dos.writeUTF("There is no player in the list");
                    dos.flush();
                } else if (UserDatabase.getPlayers().size() == 1) {
                    dos.writeUTF("You can't remove yourself");
                    dos.flush();
                } else {
                    UserDatabase.getPlayers().clear();
                    UserDatabase.getPlayers().add(UserDatabase.getUserByUsername(inputSplit[1]));
                    dos.writeUTF("All players removed successfully");
                    dos.flush();
                }
            } else if (inputSplit[0].equals("addPlayer")) {
                String usernameToAdd = inputSplit[1];
                String myUsername = inputSplit[2];
                DataOutputStream dos = new DataOutputStream(client.getOutputStream());
                if (!UserDatabase.getPlayers().contains(UserDatabase.getUserByUsername(myUsername))) {
                    UserDatabase.getPlayers().add(UserDatabase.getUserByUsername(myUsername));
                }
                for (User user : UserDatabase.getPlayers()) {
                    if (user.getUsername().equals(usernameToAdd)) {
                        dos.writeUTF("Player is already added");
                        dos.flush();
                        return;
                    }
                }
                UserDatabase.getPlayers().add(UserDatabase.getUserByUsername(usernameToAdd));
                dos.writeUTF("Player successfully added");
                dos.flush();
            } else if (inputSplit[0].equals("removeGameRequest")) {
                GameRequest gameRequest = new Gson().fromJson(inputSplit[1], GameRequest.class);
                UserDatabase.getGames().remove(gameRequest);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
