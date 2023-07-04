package controller;

import com.google.gson.Gson;
import javafx.stage.Stage;
import model.GameRequest;
import view.MainMenu;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class StartMenuController {//todo handle for gameRequests
    private static final int PORT = 6000;

    public static void removeGameRequest(GameRequest gameRequest) {
        try {
            Socket socket = new Socket("localhost", PORT);
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            String[] json = {"removeGameRequest", new Gson().toJson(gameRequest)};
            dataOutputStream.writeUTF(new Gson().toJson(json));
            dataOutputStream.flush();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String addPlayer(String username) {//todo matin
        if (username.equals(MainMenu.getCurrentUser().getUsername())) {
            return "You are already added";
        }
        if (!LoginMenuController.isUsernameUsed(username)) {
            return "No player with this username exists";
        }
        try {
            Socket socket = new Socket("localhost", PORT);
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            String[] json = {"addPlayer", username, MainMenu.getCurrentUser().getUsername()};
            dataOutputStream.writeUTF(new Gson().toJson(json));
            dataOutputStream.flush();
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String message = dataInputStream.readUTF();
            socket.close();
            return message;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    public String removePlayer(String username) {//todo matin
        if (!LoginMenuController.isUsernameUsed(username)) {
            return "No player with this username exists";
        }
        if (username.equals(MainMenu.getCurrentUser())) {
            return "You can't remove yourself";
        }
        try {
            Socket socket = new Socket("localhost", PORT);
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            String[] json = {"removePlayer", username};
            dataOutputStream.writeUTF(new Gson().toJson(json));
            dataOutputStream.flush();
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String message = dataInputStream.readUTF();
            socket.close();
            return message;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    public String removeAllPlayers() {//todo matin
        try {
            Socket socket = new Socket("localhost", PORT);
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            String[] json = {"removeAllPlayers", MainMenu.getCurrentUser().getUsername()};
            dataOutputStream.writeUTF(new Gson().toJson(json));
            dataOutputStream.flush();
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String message = dataInputStream.readUTF();
            socket.close();
            return message;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    private boolean doesPlayerExist(String username) {
        return LoginMenuController.isUsernameUsed(username);
    }

    public String canStartGame(GameRequest game) {//todo
        try {
            Socket socket = new Socket("localhost",PORT);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            String[] json = {"canStartGame", new Gson().toJson(game)};
            String output = new Gson().toJson(json);
            dos.writeUTF(output);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            boolean ok = dis.readBoolean();
            if (ok) {

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
//        if (UserDatabase.getPlayers().size() < 2) {
//            return "There must be at least 2 players";
//        }
        // if there's no map the game can't be started
        return "game started successfully";
    }

    public void playGame(Stage stage, GameRequest gameRequest) {
        String[] json = {"playGame"};
        try {
            Socket socket = new Socket("localhost", PORT);
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(new Gson().toJson(json));
            dataOutputStream.writeUTF(gameRequest.toJson());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        MapView mapView = new MapView(new Game(UserDatabase.getCurrentMap(), players));
//        mapView.start(stage);
    }
}
