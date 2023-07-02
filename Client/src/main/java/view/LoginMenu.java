package view;

import controller.LoginMenuController;
import controller.RegisterMenuController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;

public class LoginMenu extends Application {
    private Label usernameError = new Label();

    {
        usernameError.setTextFill(Color.RED);
    }

    private Label passwordError = new Label();

    {
        passwordError.setTextFill(Color.RED);
    }

    private Label questionError = new Label();

    {
        questionError.setTextFill(Color.RED);
    }

    @Override
    public void start(Stage stage) throws Exception {
        VBox vBox = new VBox();
        VBox vBox1 = new VBox(usernameError, passwordError);
        Pane pane = new Pane(vBox, vBox1);
        RegisterMenu.setBackGround(pane);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        pane.setPrefSize(width, height);
        EnterMenu.getBackToMe(stage, pane);
        TextField username = new TextField();
        username.setPromptText("Username");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");
        setVBox(vBox, width, height, username, password);
        setVBox1(vBox1, width, height);
        VBox vBox2 = getvBox2(width, height);
        pane.getChildren().add(vBox2);
        Button enter = getEnter(username, password);
        Button forgotPassword = getForgotPassword(vBox, username, vBox2);
        vBox.getChildren().addAll(forgotPassword, enter);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    private VBox getvBox2(double width, double height) {
        VBox vBox2 = new VBox(questionError);
        vBox2.setLayoutX(width / 2 - 100);
        vBox2.setLayoutY(height / 2 - 200);
        return vBox2;
    }

    private static void setVBox1(VBox vBox1, double width, double height) {
        vBox1.setLayoutX(width / 2 - 250);
        vBox1.setLayoutY(height / 2 - 200);
        vBox1.setSpacing(20);
    }

    private static void setVBox(VBox vBox, double width, double height, TextField username, PasswordField password) {
        vBox.getChildren().addAll(username, password);
        vBox.setLayoutX(width / 2 - 100);
        vBox.setLayoutY(height / 2 - 200);
        vBox.setSpacing(20);
    }

    private Button getForgotPassword(VBox vBox, TextField username, VBox vBox2) {//todo check
        Button forgotPassword = new Button("Forgot Password");
        forgotPassword.setOnMouseClicked(event -> {
            usernameError.setText("");
            passwordError.setText("");
            questionError.setText("");
            if (username.getText().equals("")) {
                usernameError.setText("Username can't be empty");
            } else if (!LoginMenuController.isUsernameUsed(username.getText())) {
                usernameError.setText("Username doesn't exist");
            } else {
                usernameError.setText("");
                vBox.getChildren().clear();
                Text text = new Text("answer the question\n" + LoginMenuController.getQuestion(username.getText()));
                text.setFill(Color.RED);
                TextField answer = new TextField();
                Button submit = getSubmit(username, vBox2, answer);
                vBox2.getChildren().addAll(text, answer, submit);
                vBox2.setSpacing(20);
            }
            if (!usernameError.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText(usernameError.getText());
                alert.showAndWait();
            }
        });
        return forgotPassword;
    }

    private Button getSubmit(TextField username, VBox vBox2, TextField answer) {
        Button submit = new Button("Submit");
        submit.setOnMouseClicked(event1 -> {
            if (answer.getText().equals("")) {
                questionError.setText("Answer can't be empty");
            } else if (!LoginMenuController.checkUserAnswer(username.getText(), answer.getText())) {
                questionError.setText("Wrong answer");
            } else {
                questionError.setText("");
                String newPassword = RegisterMenuController.generateRandomPassword();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("New Password");
                alert.setHeaderText("Your new password is:");
                alert.setContentText(newPassword);
                alert.showAndWait();
                PasswordField confirm = new PasswordField();
                confirm.setPromptText("Confirm");
                Button enter1 = getEnter1(username, vBox2, newPassword, confirm);
                vBox2.getChildren().clear();
                vBox2.getChildren().addAll(enter1, passwordError, confirm);
            }
        });
        return submit;
    }

    private Button getEnter1(TextField username, VBox vBox2, String newPassword, PasswordField confirm) {
        Button enter1 = new Button("Enter");
        enter1.setOnMouseClicked(event2 -> {
            String output = LoginMenuController.setNewPassword(username.getText(), newPassword, newPassword);
            if (confirm.getText().equals("")) {
                passwordError.setText("Password can't be empty");
            } else if (!confirm.getText().equals(newPassword)) {
                passwordError.setText("Passwords don't match");
            } else if (!output.equals("password changed successfully")) {
                passwordError.setText(output);
            } else {
                passwordError.setText("");
                vBox2.getChildren().clear();
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Password Changed");
                alert1.setHeaderText("Your password has been changed successfully");
                alert1.showAndWait();
                try {
                    //new MainMenu(LoginMenuController.getLoggedInUser()).start(EnterMenu.getStage());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return enter1;
    }

    private Button getEnter(TextField username, PasswordField password) {
        Button enter = new Button("Enter");
        enter.setOnMouseClicked(event -> {
            if (username.getText().equals("")) {
                passwordError.setText("");
                usernameError.setText("Username can't be empty");
            } else if (password.getText().equals("")) {
                usernameError.setText("");
                passwordError.setText("Password can't be empty");
            } else if (!LoginMenuController.isUsernameUsed(username.getText())) {
                passwordError.setText("");
                usernameError.setText("Username doesn't exist");
            } else {
                try {
                    if (LoginMenuController.checkPassword(username.getText(), password.getText())) {
                        usernameError.setText("");
                        passwordError.setText("");
                        CaptchaMenu captchaMenu = new CaptchaMenu();
                        captchaMenu.setAreWeLoggingIn(true);
                        captchaMenu.setLogginInUsername(username.getText());
                        captchaMenu.start(EnterMenu.getStage());
                    } else {
                        usernameError.setText("");
                        passwordError.setText("Wrong password");
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return enter;
    }
}