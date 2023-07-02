package view;

import controller.RegisterMenuController;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Captcha;
import model.User;
import model.UserDatabase;

import java.awt.*;

public class CaptchaMenu extends Application {

    private User registeringUser;
    private String userAnswer = "hi";
    private int userQuestionNumber = 1;

    public User getRegisteringUser() {
        return registeringUser;
    }

    public void setRegisteringUser(User registeringUser) {
        this.registeringUser = registeringUser;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public int getUserQuestionNumber() {
        return userQuestionNumber;
    }

    public void setUserQuestionNumber(int userQuestionNumber) {
        this.userQuestionNumber = userQuestionNumber;
    }

    private String logginInUsername;

    public void setLogginInUsername(String logginInUsername) {
        this.logginInUsername = logginInUsername;
    }

    private ImageView myCaptcha;

    @Override
    public void start(Stage stage) throws Exception {//todo:style
        Pane pane = new Pane();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        pane.setPrefSize(width, height);
        setImage();
        TextField answerField = new TextField();
        answerField.setPromptText("answer here");
        Button newCaptcha = new Button("new");
        Button submit = new Button("submit");
        HBox hBox = new HBox(answerField, newCaptcha);
        hBox.setSpacing(30);
        VBox vBox = new VBox(myCaptcha, hBox, submit);
        vBox.setSpacing(30);
        vBox.setAlignment(Pos.CENTER);
        vBox.setLayoutX(width / 2 - 100);
        vBox.setLayoutY(height / 2 - 100);
        pane.getChildren().add(vBox);
        newCaptcha.setOnMouseClicked(event -> {
            showNewCaptcha(submit, hBox, vBox);
        });
        submittingAction(answerField, submit);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    private void submittingAction(TextField answerField, Button submit) {
        submit.setOnMouseClicked(event -> {
            if (UserDatabase.isAreWeLoggingIn()) {
                loginCaptcha(answerField);
            } else {
                registerProcess(answerField);
            }
        });
    }

    private void registerProcess(TextField answerField) {
        try {
            int a = Integer.parseInt(answerField.getText());
            if (a == Captcha.getMyNumber()) {
                RegisterMenuController.addUser(registeringUser, userAnswer, userQuestionNumber);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("registered successfully");
                alert.showAndWait();
                try {
                    new EnterMenu().start(EnterMenu.getStage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                showErrorAlert();
            }
        } catch (NumberFormatException e) {
            showErrorAlert();
        }
    }

    private void loginCaptcha(TextField answerField) {
        try {
            int a = Integer.parseInt(answerField.getText());
            if (a == Captcha.getMyNumber()) {
                UserDatabase.setCurrentUser(UserDatabase.getUserByUsername(logginInUsername));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("loggedIn successfully");
                alert.showAndWait();
                try {
                    UserDatabase.setCurrentUser(UserDatabase.getUserByUsername(logginInUsername));
                    new MainMenu().start(EnterMenu.getStage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                showErrorAlert();
            }
        } catch (NumberFormatException e) {
            showErrorAlert();
        }
    }

    private void showNewCaptcha(Button submit, HBox hBox, VBox vBox) {
        myCaptcha.setImage(null);
        vBox.getChildren().remove(myCaptcha);
        vBox.getChildren().remove(hBox);
        vBox.getChildren().remove(submit);
        setImage();
        vBox.getChildren().add(myCaptcha);
        vBox.getChildren().add(hBox);
        vBox.getChildren().add(submit);
    }

    private void showErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("please type in the correct captcha");
        alert.show();
    }

    private void setImage() {
        Captcha captcha = new Captcha();
        myCaptcha = new ImageView(captcha.createCaptcha());
    }
}
