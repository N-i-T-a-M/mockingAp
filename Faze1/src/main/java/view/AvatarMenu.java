package view;

import Enums.Avatars;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.User;
import model.UserDatabase;

import java.awt.*;
import java.io.File;

;

public class AvatarMenu extends Application {//todo debug it
    private ImageView avatarImageView = null;
    private static String address = UserDatabase.getCurrentUser().getAvatar();

    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = new Pane();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        pane.setPrefSize(width, height);
        RegisterMenu.setBackGround(pane);
        Button back = getBack(stage);
        GridPane gridPane = getGridPane(width, height);
        Circle avatar = getCircle(width);
        //todo: add avatars to gridPane
        addAvatar(gridPane, Avatars.AVATAR1.getAddress(), 0, 0, avatar);
        addAvatar(gridPane, Avatars.AVATAR2.getAddress(), 0, 1, avatar);
        ////////////////////////////////////////////////////////////////////////
        Button addAvatar = getAddAvatar(stage, avatar);
        Button avatarByDragging = getAvatarByDragging(pane, width, height, avatar);
        addAvatar.setLayoutX(width / 2 - 100);
        addAvatar.setLayoutY(height / 2 + 100);
        pane.getChildren().addAll(back, gridPane, addAvatar, avatar, avatarByDragging);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }


    private static GridPane getGridPane(double width, double height) {
        GridPane gridPane = new GridPane();
        gridPane.setLayoutX(width / 2 - 100);
        gridPane.setLayoutY(height / 2 - 200);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        return gridPane;
    }


    private static Circle getCircle(double width) {
        Circle avatar = new Circle(50);
        avatar.setCenterX(width - 50);
        avatar.setCenterY(50);
        avatar.setFill(new ImagePattern(new Image(UserDatabase.getCurrentUser().getAvatar())));
        return avatar;
    }

    private static Button getAddAvatar(Stage stage, Circle avatar) {
        Button addAvatar = new Button("choose avatar from system");
        addAvatar.setOnMouseClicked(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose your avatar");
            FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Image files", "*.jpg", "*.png", ".gif");
            fileChooser.getExtensionFilters().add(extensionFilter);
            File initialDirectory = new File(System.getProperty("user.home"));
            fileChooser.setInitialDirectory(initialDirectory);
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                Image image = new Image(selectedFile.toURI().toString());
                address = selectedFile.toURI().toString();
                avatar.setFill(new ImagePattern(image));
                UserDatabase.getCurrentUser().setAvatar(selectedFile.toURI().toString());
                address = selectedFile.toURI().toString();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Avatar changed successfully");
                alert.showAndWait();
            }
        });
        return addAvatar;
    }

    private Button getAvatarByDragging(Pane pane, double width, double height, Circle avatar) {
        Button avatarByDragging = new Button("choose avatar by dragging");
        avatarByDragging.setLayoutX(width / 2 - 100);
        avatarByDragging.setLayoutY(height / 2 + 150);
        avatarByDragging.setOnMouseClicked(event -> {
            avatarImageView = new ImageView();
            avatarImageView.setFitWidth(100);
            avatarImageView.setFitHeight(100);
            // create a StackPane to hold the ImageView
            StackPane root = new StackPane();
            root.getChildren().add(avatarImageView);
            pane.getChildren().add(root);
            root.setLayoutX(width - 100);
            root.setLayoutY(0);
            // set the drag-and-drop behavior for the StackPane
            root.setOnDragOver(event1 -> {
                if (event1.getDragboard().hasFiles()) {
                    event1.acceptTransferModes(javafx.scene.input.TransferMode.COPY);
                }
                event1.consume();
            });

            root.setOnDragDropped(event1 -> {
                if (event1.getDragboard().hasFiles()) {
                    Image image = new Image(event1.getDragboard().getFiles().get(0).toURI().toString());
                    avatarImageView.setImage(image);
                    UserDatabase.getCurrentUser().setAvatar(image.getUrl().toString());
                    pane.getChildren().remove(avatar);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Avatar changed successfully");
                    alert.showAndWait();
                }
                event1.setDropCompleted(true);
                event1.consume();
            });
        });
        return avatarByDragging;
    }

    private static Button getBack(Stage stage) {
        Button back = new Button("Back");
        back.setOnMouseClicked(event -> {
            try {
                UserDatabase.getCurrentUser().setAvatar(address);
                new ProfileMenu().start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return back;
    }

    private static void addAvatar(GridPane gridPane, String address, int i, int j, Circle avatar) {
        Image image = new Image(address);
        Circle circle = new Circle();
        circle.setRadius(50);
        circle.setFill(new ImagePattern(image));
        gridPane.add(circle, i, j);
        circle.setOnMouseClicked(event -> {
            avatar.setFill(new ImagePattern(image));
            UserDatabase.getCurrentUser().setAvatar(address);
            AvatarMenu.address = address;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Avatar changed successfully");
            alert.showAndWait();
        });
    }
}
