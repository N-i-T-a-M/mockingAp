package view.lobby;

import controller.StartMenuController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.GameRequest;
import model.UserDatabase;
import view.ChatMenu;
import view.RegisterMenu;

import java.awt.*;


public class StartMenu extends Application {
    private static GameRequest gameRequest;

    public StartMenu(GameRequest gameRequest) {
        StartMenu.gameRequest = gameRequest;
    }

    @Override
    public void start(Stage stage) throws Exception {
        StartMenuController controller = new StartMenuController();
        HBox setPlayers = new HBox();
        VBox setMap = new VBox();
        Pane pane = new Pane();
        RegisterMenu.setBackGround(pane);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        pane.setPrefSize(width, height);
        Button back = getBack(stage);
        TextField addPlayer = new TextField();
        Button addPlayerButton = getAddPlayerButton(controller, setPlayers, width, addPlayer);
        Button removePlayer = getRemovePlayer(controller, width, addPlayer);
        Button removeAllPlayers = getRemoveAllPlayers(controller);
        Button startGame = getStartGame(stage, width);
        Button chat = getChat(stage);
        Text id = getID(stage);

        setPlayers.getChildren().addAll(addPlayer, addPlayerButton, removePlayer, removeAllPlayers);
        pane.getChildren().addAll(back, setPlayers, setMap, startGame, chat, id);
        addPrivacy(pane, stage, width, height);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    private static Text getID(Stage stage) {
        Text id = new Text("ID: " + gameRequest.getId());
        id.setLayoutX(50);
        id.setLayoutY(25);
        return id;
    }

    private static void addPrivacy(Pane pane, Stage stage, double width, double length) {
        HBox privacy = privacy(stage, width, length);
        if (gameRequest.getAdmin() == UserDatabase.getCurrentUser()) {
            pane.getChildren().add(privacy);
        }
    }

    private static HBox privacy(Stage stage, double width, double length) {
        ToggleButton privacy = new ToggleButton("privacy");
        Text status = new Text();
        HBox holder = new HBox(privacy, status);
        privacy.setOnMouseClicked(event -> {
            if (privacy.isSelected()) {
                status.setText("private");
                gameRequest.setPublic(false);
            } else {
                status.setText("public");
                gameRequest.setPublic(true);
            }
        });
        privacy.setSelected(false);
        holder.setLayoutY(100);
        holder.setLayoutX(5);
        return holder;
    }

    private static Button getRemovePlayer(StartMenuController controller, double width, TextField addPlayer) {
        Button removePlayer = new Button("removePlayer");
        removePlayer.setOnMouseClicked(event -> {
            String output = controller.removePlayer(addPlayer.getText());
            Alert alert;
            if (output.equals("Player removed successfully")) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(output);
                addPlayer.setText("");
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(output);
            }
            alert.show();
        });
        removePlayer.setLayoutX(width / 2 - 100);
        removePlayer.setLayoutY(100);
        return removePlayer;
    }

    private static Button getRemoveAllPlayers(StartMenuController controller) {
        Button removeAllPlayers = new Button("removeAllPlayers");
        removeAllPlayers.setOnMouseClicked(event -> {
            Alert alert;
            String output = controller.removeAllPlayers();
            if (output.equals("All Players removed successfully")) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(output);
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(output);
            }
            alert.show();
        });
        return removeAllPlayers;
    }

    private static Button getStartGame(Stage stage, double width) {
        Button startGame = new Button("startGame");
        startGame.setLayoutX(width - 100);
        startGame.setLayoutY(5);
        startGame.setOnMouseClicked(mouseEvent -> {
            try {
                (new StartMenuController()).playGame(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return startGame;
    }

    private static Button getAddPlayerButton(StartMenuController controller, HBox setPlayers, double width, TextField addPlayer) {
        Button addPlayerButton = new Button("Add Player");
        addPlayer.setPromptText("Username");
        addPlayerButton.setOnMouseClicked(event -> {
            Alert alert;

            if (addPlayer.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Username can't be empty");
                alert.show();
            }
            String output = controller.addPlayer(addPlayer.getText());
            if (output.equals("Player successfully added")) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(output);
                addPlayer.setText("");
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(output);
            }
            alert.show();
        });
        setPlayers.setLayoutX(width / 2 - 100);
        setPlayers.setLayoutY(50);
        return addPlayerButton;
    }

    private static Button getBack(Stage stage) {
        Button back = new Button("Back");
        back.setOnMouseClicked(event -> {
            try {
                if (gameRequest.getAdmin() == UserDatabase.getCurrentUser()) {
                    gameRequest.getPlayers().remove(UserDatabase.getCurrentUser());
                    UserDatabase.getCurrentUser().setGameRequest(null);
                    if (gameRequest.getPlayers().size() == 0) {
                        UserDatabase.getGames().remove(gameRequest);
                        new Lobby().start(stage);
                    } else {
                        gameRequest.setAdmin(gameRequest.getPlayers().get(0));
                    }
                } else {
                    gameRequest.getPlayers().remove(UserDatabase.getCurrentUser());
                    UserDatabase.getCurrentUser().setGameRequest(null);
                }
                new Lobby().start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        back.setLayoutX(5);
        back.setLayoutY(5);
        return back;
    }

    private static Button getChat(Stage stage) {
        Button chat = new Button("chat");
        chat.setLayoutY(50);
        chat.setLayoutX(5);
        chat.setOnMouseClicked(mouseEvent -> {
            ChatMenu menu = new ChatMenu();
            menu.setComingFromLobby(true);
            menu.setCurrentChat(gameRequest.getChat());
            menu.setCurrentUser(UserDatabase.getCurrentUser());
            try {
                menu.start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return chat;
    }
}
//    public void run(Scanner scanner) {
//        System.out.println("Welcome to start menu");
//        String input;
//        Matcher addPlayer, removePlayer, removeAllPlayers, startGame, addRandomPlayers;
//        StartMenuController controller = new StartMenuController();
//        while (true) {
//            input = scanner.nextLine();
//            addPlayer = StartMenuCommands.getMatcher(input, StartMenuCommands.ADD_PLAYER);
//            removePlayer = StartMenuCommands.getMatcher(input, StartMenuCommands.REMOVE_PLAYER);
//            removeAllPlayers = StartMenuCommands.getMatcher(input, StartMenuCommands.REMOVE_ALL_PLAYERS);
//            startGame = StartMenuCommands.getMatcher(input, StartMenuCommands.START_GAME);
//            if (input.equalsIgnoreCase("back")) {
//                System.out.println("Welcome to main menu");
//                return;
//            } else if (addPlayer.find()) {
//                if (addPlayer.group("username").isEmpty()) {
//                    System.out.println("Username can't be empty");
//                    continue;
//                }
//                System.out.println(controller.addPlayer(addPlayer.group("username")));
//            } else if (removePlayer.find()) {
//                if (removePlayer.group("username").isEmpty()) {
//                    System.out.println("Username can't be empty");
//                    continue;
//                }
//                System.out.println(controller.removePlayer(removePlayer.group("username")));
//            } else if (removeAllPlayers.find()) {
//                System.out.println(controller.removeAllPlayers());
//            } else if (startGame.find()) {
//                String output = controller.canStartGame();
//                System.out.println(output);
//                if (output.equals("game started successfully")) {
//                    controller.playGame(scanner);
//                }
//            } else {
//                System.out.println("Invalid command!");
//            }
//        }
//    }
