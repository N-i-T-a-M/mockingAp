package controller;

import com.google.gson.Gson;
import javafx.stage.Stage;
import model.GameRequest;
import view.MainMenu;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class StartMenuController {
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

    public String addPlayer(String username) {
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

    public String removePlayer(String username) {
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

    public String removeAllPlayers() {
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
//
//    public String canStartGame() {
//        if (UserDatabase.getPlayers().size() < 2) {
//            return "There must be at least 2 players";
//        }
//        // if there's no map the game can't be started
//        return "game started successfully";
//    }
//
//    public void playGame(Scanner scanner) {
//        GameMenu menu = new GameMenu();
//        ArrayList<Kingdom> players = new ArrayList<>();
//        for (int i = 0; i < UserDatabase.getPlayers().size(); i++) {
//            Kingdom kingdom = new Kingdom(UserDatabase.getPlayers().get(i), UserDatabase.getCurrentMap().getHeadSquares().get(i));
//            players.add(kingdom);
//            kingdom.addToStockPiles(new Storage(BuildingType.STOCKPILE, kingdom,
//                    kingdom.getHeadSquare().getxCoordinate() - 1,
//                    kingdom.getHeadSquare().getyCoordinate()));
//            UserDatabase.getCurrentMap().getMap()[kingdom.getHeadSquare().getxCoordinate() - 1][kingdom.getHeadSquare().getyCoordinate()].setBuilding(kingdom.getStockPiles().get(0));
//        }
//        menu.run(scanner, new Game(UserDatabase.getCurrentMap(), players));
//    }
//
//    public void playGame(Stage stage) throws Exception {
//        ArrayList<Kingdom> players = new ArrayList<>();
//        if (UserDatabase.getCurrentMap() == null)
//            UserDatabase.setCurrentMap(new Map(30, 3));
//        for (int i = 0; i < UserDatabase.getPlayers().size(); i++) {
//            Kingdom kingdom = new Kingdom(UserDatabase.getPlayers().get(i), UserDatabase.getCurrentMap().getHeadSquares().get(i));
//            players.add(kingdom);
//            kingdom.addToStockPiles(new Storage(BuildingType.STOCKPILE, kingdom,
//                    kingdom.getHeadSquare().getxCoordinate() - 1,
//                    kingdom.getHeadSquare().getyCoordinate()));
//            UserDatabase.getCurrentMap().getMap()[kingdom.getHeadSquare().getxCoordinate() - 1][kingdom.getHeadSquare().getyCoordinate()].setBuilding(kingdom.getStockPiles().get(0));
//        }
//        MapView mapView = new MapView(new Game(UserDatabase.getCurrentMap(), players));
//        mapView.start(stage);
//    }
}