package view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.map.Map;


import java.util.Scanner;

public class PauseMenu extends Application {
    private Stage stage;
    private Map map;

    public PauseMenu(Stage stage, Map map) {
        this.map = map;
        this.stage = stage;
    }

    public String run(Scanner scanner) {
        System.out.println("Welcome to pause menu!");
        String input;
        while (true) {
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Are you sure you want to exit? (yes/anything else)");
                input = scanner.nextLine();
                if (input.equalsIgnoreCase("yes")) {
                    return "exit";
                } else {
                    System.out.println("exit canceled");
                }
            } else if (input.equalsIgnoreCase("resume")) {
                return "resume";
            } else {
                System.out.println("Invalid command!");
            }
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        Button resumeButton = new Button("resume");
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(resumeButton);
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.show();

        resumeButton.setOnMouseClicked(mouseEvent -> {
            try {
                (new MapView(MapViewController.getInstance(map))).start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

    }
}
