package controller;

import com.google.gson.Gson;
import model.User;
import model.UserDatabase;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class LobbyController {
    public void handle(Socket client) {

        try {
            DataInputStream dis = new DataInputStream(client.getInputStream());
            String string = dis.readUTF();
            System.out.println(string);
            String[] inputSplit = new Gson().fromJson(string, String[].class);
            for (int i = 0; i < inputSplit.length; i++) {
                System.out.print(inputSplit[i] + " ");
            }
            if (inputSplit[0].equals("getGameRequestById")) {
                DataOutputStream dos = new DataOutputStream(client.getOutputStream());
                long id = Long.parseLong(inputSplit[1]);
                if (UserDatabase.getGameById(id) == null) {
                    dos.writeUTF("null");
                } else {
                    dos.writeUTF((new Gson()).toJson(UserDatabase.getGameById(id)));
                }
            }
            else if (inputSplit[0].equals("createGameRequest")) {
                String gameRequestJson = inputSplit[1];
                UserDatabase.getGames().add((new Gson()).fromJson(gameRequestJson, model.GameRequest.class));
                User user = (new Gson().fromJson(inputSplit[2], User.class));
                UserDatabase.updateUser(user);
            }
            else if (inputSplit[0].equals("getGames")) {
                DataOutputStream dos = new DataOutputStream(client.getOutputStream());
                dos.writeUTF((new Gson()).toJson(UserDatabase.getGames()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
