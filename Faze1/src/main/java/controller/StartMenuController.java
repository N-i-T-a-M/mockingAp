package controller;

import javafx.stage.Stage;
import model.Building.BuildingType;
import model.Building.Storage;
import model.Game;
import model.Kingdom;
import model.User;
import model.UserDatabase;
import model.map.Map;
import view.GameMenu;
import view.MapView;

import java.util.ArrayList;
import java.util.Scanner;

public class StartMenuController {
    public String addPlayer(String username) {
        if (!UserDatabase.getPlayers().contains(UserDatabase.getCurrentUser())) {
            UserDatabase.getPlayers().add(UserDatabase.getCurrentUser());
        }
        if (username.equals(UserDatabase.getCurrentUser().getUsername())) {
            return "You are already added";
        }
        if (!doesPlayerExist(username)) {
            return "No player with this username exists";
        }
        for (User user : UserDatabase.getPlayers()) {
            if (user.getUsername().equals(username)) {
                return "Player is already added";
            }
        }
//        if (UserDatabase.getPlayers().size() > UserDatabase.getCurrentMap().getHeadSquares().size() - 1)
//            return "The map is filled!";
        UserDatabase.getPlayers().add(UserDatabase.getUserByUsername(username));
        return "Player successfully added";
    }

    public String removePlayer(String username) {
        if (!doesPlayerExist(username)) {
            return "No player with this username exists";
        }
        for (User user : UserDatabase.getPlayers()) {
            if (user.getUsername().equals(username)) {
                if (user.getUsername().equals(UserDatabase.getCurrentUser().getUsername())) {
                    return "You can't remove yourself";
                }
                UserDatabase.getPlayers().remove(user);
                return "Player removed successfully";
            }
        }
        return "The player with this username isn't added";
    }

    public String removeAllPlayers() {
        if (UserDatabase.getPlayers().size() == 0) {
            return "There's no player to remove!";
        }
        if (UserDatabase.getPlayers().size() == 1) {
            return "You can't remove yourself";
        }
        UserDatabase.getPlayers().clear();
        UserDatabase.getPlayers().add(UserDatabase.getCurrentUser());
        return "All Players removed successfully";
    }

    private boolean doesPlayerExist(String username) {
        return UserDatabase.getUserByUsername(username) != null;
    }

    public String canStartGame() {
        if (UserDatabase.getPlayers().size() < 2) {
            return "There must be at least 2 players";
        }
        // if there's no map the game can't be started
        return "game started successfully";
    }

    public void playGame(Scanner scanner) {
        GameMenu menu = new GameMenu();
        ArrayList<Kingdom> players = new ArrayList<>();
        for (int i = 0; i < UserDatabase.getPlayers().size(); i++) {
            Kingdom kingdom = new Kingdom(UserDatabase.getPlayers().get(i), UserDatabase.getCurrentMap().getHeadSquares().get(i));
            players.add(kingdom);
            kingdom.addToStockPiles(new Storage(BuildingType.STOCKPILE, kingdom,
                    kingdom.getHeadSquare().getxCoordinate() - 1,
                    kingdom.getHeadSquare().getyCoordinate()));
            UserDatabase.getCurrentMap().getMap()[kingdom.getHeadSquare().getxCoordinate() - 1][kingdom.getHeadSquare().getyCoordinate()].setBuilding(kingdom.getStockPiles().get(0));
        }
        menu.run(scanner, new Game(UserDatabase.getCurrentMap(), players));
    }

    public void playGame(Stage stage) throws Exception {
        ArrayList<Kingdom> players = new ArrayList<>();
        if(UserDatabase.getCurrentMap() == null)
            UserDatabase.setCurrentMap(new Map(30, 3));
        for (int i = 0; i < UserDatabase.getPlayers().size(); i++) {
            Kingdom kingdom = new Kingdom(UserDatabase.getPlayers().get(i), UserDatabase.getCurrentMap().getHeadSquares().get(i));
            players.add(kingdom);
            kingdom.addToStockPiles(new Storage(BuildingType.STOCKPILE, kingdom,
                    kingdom.getHeadSquare().getxCoordinate() - 1,
                    kingdom.getHeadSquare().getyCoordinate()));
            UserDatabase.getCurrentMap().getMap()[kingdom.getHeadSquare().getxCoordinate() - 1][kingdom.getHeadSquare().getyCoordinate()].setBuilding(kingdom.getStockPiles().get(0));
        }
        MapView mapView = new MapView(new Game(UserDatabase.getCurrentMap(), players));
        mapView.start(stage);
    }
}
