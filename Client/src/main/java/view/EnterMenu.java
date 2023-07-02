package view;

import Enums.Images;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;

public class EnterMenu extends Application {// todo : fix the details missing

    public static void main(String[] args) {
        launch(args);
    }

    private static Stage stage;

    public static Stage getStage() {
        return stage;
    }

    @Override
    public void start(Stage stage) throws Exception {
        EnterMenu.stage = stage;
        Pane pane = new Pane();
        Background background = getBackground();
        pane.setBackground(background);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        pane.setPrefSize(width, height);
        Text text = getWelcomeText(width);
        Button register = getRegister(width, height);
        Button login = getLogin(width, height);
        Button exit = getExit(width, height);
        //NOT NECESSARY
//        Button skip = new Button("skip");
//        skip.setOnAction(actionEvent -> {
//            try {
//                User user = new User("admin", "admin", "admin","admin","admin");
//                UserDatabase.setCurrentUser(user);
//                //new MainMenu().start(stage);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//        pane.getChildren().add(skip);

        pane.getChildren().addAll(text, login, register, exit);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    private static Background getBackground() {
        Image image = new Image(Images.BACK_GROUND4.getAddress());
        BackgroundImage backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        return background;
    }

    private static Text getWelcomeText(double width) {
        Text text = new Text("Welcome to Strong hold!");
        text.setFill(Color.BLACK);
        text.setX(width / 2 - text.getLayoutBounds().getWidth() / 2);
        text.setY(30);
        return text;
    }

    private Button getLogin(double width, double height) {
        Button login = new Button("Login");
        login.setLayoutX(width / 2 - 50);
        login.setPrefWidth(100);
        login.setLayoutY(height / 2 - 30);
        login.setPrefHeight(30);
        login.setOnAction(actionEvent -> {
            try {
                login(actionEvent);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return login;
    }

    private Button getRegister(double width, double height) {
        Button register = new Button("Register");
        register.setLayoutX(width / 2 - 50);
        register.setPrefWidth(100);
        register.setLayoutY(height / 2 - 80);
        register.setPrefHeight(30);
        register.setOnAction(actionEvent -> {
            register.setAlignment(Pos.CENTER);
            try {
                register(actionEvent);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return register;
    }

    private Button getExit(double width, double height) {
        Button exit = new Button("Exit");
        exit.setLayoutY(0);
        exit.setLayoutX(width - 50);
        exit.setPrefWidth(50);
        exit.setPrefHeight(30);
        exit.setOnAction(actionEvent -> {
            exit.setAlignment(Pos.CENTER);
            exit(actionEvent);
        });
        return exit;
    }

    private void exit(ActionEvent actionEvent) {
        System.exit(0);
    }

    private void login(ActionEvent actionEvent) throws Exception {
        new LoginMenu().start(stage);
    }

    public void register(ActionEvent actionEvent) throws Exception {

        new RegisterMenu().start(stage);
    }

    public static void getBackToMe(Stage stage, Pane pane) {
        Button back = new Button("back");
        pane.getChildren().add(back);
        back.setLayoutX(0);
        back.setLayoutY(0);
        back.setOnMouseClicked(event -> {
            try {
                new EnterMenu().start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}

// public void run(Scanner scanner) {
// String input;
// EnterMenuController controller = new EnterMenuController();
// System.out.println("Do you already have an account?\n(Please type in exit if
// you want to end the program or answer with \"register now\" or \"login
// now\")");
// while (true) {
// input = scanner.nextLine();
// if (input.equals("login now")) {
// LoginMenu menu = new LoginMenu();
// System.out.println("Please enter your username and password to login");
// try {
// menu.run(scanner);
// } catch (NoSuchAlgorithmException e) {
// throw new RuntimeException(e);
// }
// controller.resetStayLoggedIn();
// System.out.println("Welcome back to enter menu!");
// } else if (input.equals("register now")) {
// RegisterMenu menu = new RegisterMenu();
// System.out.println("Please enter your information to register");
// menu.run(scanner);
// controller.saveUsers();
// System.out.println("Welcome back to enter menu!");
// } else if (input.equalsIgnoreCase("exit")) {
// System.out.println("Thanks for being with us!");
// controller.saveUsers();
// System.exit(0);
// } else {
// System.out.println("Please try again!");
// }
// }
// }
