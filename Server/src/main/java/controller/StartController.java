package controller;

import Enums.BuildingType;
import com.google.gson.Gson;
import model.*;
import model.Building.Storage;
import model.map.Map;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

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
            } else if (inputSplit[0].equals("playGame")) {
                GameRequest gameRequest = new Gson().fromJson(dis.readUTF(), GameRequest.class);
                ArrayList<Kingdom> players = new ArrayList<>();
                Map currentMap = new Map(30, 3);
                for (int i = 0; i < gameRequest.getPlayers().size(); i++) {
                    Kingdom kingdom = new Kingdom(gameRequest.getPlayers().get(i), currentMap.getHeadSquares().get(i));
                    players.add(kingdom);
                    kingdom.addToStockPiles(new Storage(BuildingType.STOCKPILE, kingdom,
                            kingdom.getHeadSquare().getxCoordinate() - 1,
                            kingdom.getHeadSquare().getyCoordinate()));
                    currentMap.getMap()[kingdom.getHeadSquare().getxCoordinate() - 1][kingdom.getHeadSquare().getyCoordinate()].setBuilding(kingdom.getStockPiles().get(0));
                }
                Game game = new Game(currentMap, players);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
