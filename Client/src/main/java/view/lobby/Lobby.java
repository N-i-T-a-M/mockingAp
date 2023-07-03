package view.lobby;

import controller.LobbyController;
import controller.ProfileController;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.GameRequest;
import model.User;
import view.MainMenu;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class Lobby extends Application {
    private static User currentUser;

    @Override
    public void start(Stage stage) throws Exception {
        currentUser = MainMenu.getCurrentUser();
        Pane pane = new Pane();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        Button back = getBackButton(width, height, stage);
        VBox games = getGames(width, height, stage);
        ImageView refresh = getRefresh(pane, games, width, height, stage);
        HBox createGame = getCreateGame(width, height, stage);
        HBox search = searchForGame(width, height, stage);
        pane.getChildren().addAll(back, games, refresh, createGame, search);
        Scene scene = new Scene(pane, width, height);
        stage.setScene(scene);
        stage.setTitle("Lobby");
        stage.show();
    }

    private HBox searchForGame(double width, double height, Stage stage) {
        TextField field = new TextField();
        Button ok = new Button("search");
        field.setPromptText("search");
        HBox search = new HBox(field, ok);
        search.setLayoutX(width / 2 - 100);
        search.setLayoutY(5);
        search.setSpacing(20);
        ok.setOnMouseClicked(mouseEvent -> {
            long id;
            try {
                id = Long.parseLong(field.getText());
                GameRequest game;
                if ((game = LobbyController.getGameRequestById(id)) != null) {
                    if (currentUser.getGameRequest() == null) {
                        currentUser.setGameRequest(game);
                        game.getPlayers().add(currentUser);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("You joined the game");
                        alert.showAndWait();
                        ProfileController profileController = new ProfileController(currentUser);
                        profileController.changeUsername(currentUser.getUsername());
                        new StartMenu(game).start(stage);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("You are already in a game");
                        alert.showAndWait();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("No game with this id");
                    alert.showAndWait();
                }
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Invalid id");
                alert.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return search;
    }

    private HBox getCreateGame(double width, double height, Stage stage) {
        Button createGame = new Button("Create Game");
        createGame.setStyle("-fx-background-color: #ff0000; -fx-text-fill: #ffffff; -fx-font-size: 20");
        createGame.setOnMouseEntered(mouseEvent -> {
            createGame.setStyle("-fx-background-color: #ff0000; -fx-text-fill: #ffffff; -fx-font-size: 25");
            createGame.setCursor(Cursor.HAND);
        });
        createGame.setOnMouseExited(mouseEvent -> {
            createGame.setStyle("-fx-background-color: #ff0000; -fx-text-fill: #ffffff; -fx-font-size: 20");
            createGame.setCursor(Cursor.DEFAULT);
        });
        TextField capacity = new TextField();
        HBox hBox = new HBox(createGame, capacity);
        createGame.setOnMouseClicked(mouseEvent -> {
            if (currentUser.getGameRequest() != null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You are already in a game");
                alert.show();
            } else {
                int cap;
                try {
                    cap = Integer.parseInt(capacity.getText());
                    if (cap > 1 && cap < 9) {
                        GameRequest gameRequest = LobbyController.createGameRequest(cap, currentUser);
                        ProfileController profileController = new ProfileController(currentUser);
                        profileController.changeUsername(currentUser.getUsername());
                        new StartMenu(gameRequest).start(stage);
                    }
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Invalid capacity");
                    alert.show();
                } catch (Exception e) {
                    throw new RuntimeException();
                }

            }
        });
        hBox.setLayoutX(width / 2);
        hBox.setLayoutY(height / 2 - 100);
        hBox.setSpacing(20);
        return hBox;
    }

    private ImageView getRefresh(Pane pane, VBox vBox, double width, double height, Stage stage) {
        ImageView refresh = new ImageView(new Image(getClass().getResource("/Avatars/refresh.png").toExternalForm()));
        refresh.setLayoutY(20);
        refresh.setLayoutX(width - 50);
        refresh.setFitWidth(40);
        refresh.setFitHeight(40);
        refresh.setOnMouseEntered(mouseEvent -> {
            refresh.setFitWidth(50);
            refresh.setFitHeight(50);
        });
        refresh.setOnMouseClicked(mouseEvent -> {
            updateBox(pane, vBox, width, height, stage);
        });
        refresh.setOnMouseExited(mouseEvent -> {
            refresh.setFitHeight(40);
            refresh.setFitWidth(40);
        });
        return refresh;
    }

    private VBox getGames(double width, double height, Stage stage) {
        VBox vBox = new VBox();
        vBox.setLayoutX(width / 2 - 400);
        vBox.setLayoutY(50);
        Text games1 = new Text("Games");
        games1.setStyle("-fx-font-size: 30");
        vBox.getChildren().add(games1);
        vBox.setSpacing(10);
        ArrayList<GameRequest> games = new ArrayList<>(10);
        Collections.shuffle(LobbyController.getGames());
        int cnt = 0;
        for (GameRequest game : LobbyController.getGames()) {
            if (game.isPublic() && cnt < 10) {
                games.add(game);
                cnt++;
            }
        }
        cnt = 1;
        for (GameRequest game : games) {
            Text text = new Text(cnt + ") Capacity : " + (game.getCapacity() - game.getPlayers().size()) + "\n");
            for (User user : game.getPlayers()) {
                text.setText(text.getText() + user.getNickname() + "\n");
            }
            Button button = new Button("Join");
            VBox vBox1 = new VBox(text, button);
            vBox1.setSpacing(10);
            button.setOnMouseClicked(mouseEvent -> {
                if (game.getCapacity() > game.getPlayers().size()) {
                    if (currentUser.getGameRequest() != null) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("You are already in a game");
                        alert.show();
                    } else {
                        currentUser.setGameRequest(game);
                        game.getPlayers().add(currentUser);
                        game.setTimeOfLastEntry(System.currentTimeMillis());
                        ProfileController profileController = new ProfileController(currentUser);
                        profileController.changeUsername(currentUser.getUsername());
                        try {
                            new StartMenu(game).start(stage);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
            vBox.getChildren().add(vBox1);
        }
        return vBox;
    }

    private void updateBox(Pane pane, VBox vBox, double width, double height, Stage stage) {
        if (pane.getChildren().contains(vBox)) {
            pane.getChildren().remove(vBox);
        }
        VBox vBox1 = getGames(width, height, stage);
        pane.getChildren().add(vBox1);
    }

    private Button getBackButton(double width, double height, Stage stage) {
        Button button = new Button("Back");
        button.setPrefSize(50, 30);
        button.setLayoutX(5);
        button.setLayoutY(5);
        button.setOnAction(actionEvent -> {
            try {
                ProfileController profileController = new ProfileController(currentUser);
                profileController.changeUsername(currentUser.getUsername());
                new MainMenu(currentUser).start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return button;
    }
}
