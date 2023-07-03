package view;

import Enums.Errors;
import Enums.Images;
import controller.RegisterMenuController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class RegisterMenu extends Application {//todo: why aren't the textFields working?
    private RegisterMenuController controller = new RegisterMenuController();
    private String registerUsername = "";
    private String registerPassword = "";
    private String registerSlogan = "";
    private String registerNickname = "";
    private String registerEmail = "";
    private Label usernameErrorLabel = new Label("Username can't be empty");
    private Label passwordErrorLabel = new Label("Password is too short");
    private Label nicknameErrorLabel = new Label();
    private Label emailErrorLabel = new Label();
    private TextField sloganField;
    private Label sloganErrorLabel = new Label();
    private Button randomSlogan = new Button("Random Slogan");
    private CheckBox famousSlogans = new CheckBox("Famous Slogans");
    private Text slogans = new Text();
    private boolean isUsernameOkay = false;
    private boolean isPasswordOkay = false;


    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = new Pane();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        pane.setPrefSize(width, height);
        setErrorLabels(width, height);
        EnterMenu.getBackToMe(stage, pane);
        TextField username = usernameField(width, height);
        PasswordField password = passwordField(width, height, pane);
        TextField visiblePassword = passwordFieldAsTextField(width, height, pane);
        CheckBox showPassword = getShowPassword(pane, width, height, password, visiblePassword);
        Hyperlink randomPassword = getRandomPassword(width, height, password, visiblePassword);
        TextField email = emailField(width, height);
        setRegisterEmail(width, height, email);
        TextField nickname = nicknameField(width, height);
        setRegisterNickname(width, height, nickname);
        CheckBox wantSlogan = getSloganCheckBox(width, height);
        Button reset = resetButton(pane, username, password, email, nickname);
        Button submit = submitButton(pane);
        Label label = getSloganLabel(pane, width, height, wantSlogan, reset, submit);
        setBackGround(pane);
        pane.getChildren().add(username);
        pane.getChildren().add(password);
        pane.getChildren().addAll(usernameErrorLabel, randomPassword, passwordErrorLabel, email, nickname, label, wantSlogan, emailErrorLabel, nicknameErrorLabel, showPassword);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    private void setErrorLabels(double width, double height) {
        passwordErrorLabel.setTextFill(Color.RED);
        passwordErrorLabel.setLayoutX(width / 2 - 250);
        passwordErrorLabel.setLayoutY(height / 2 - 50);
        passwordErrorLabel.setPrefSize(300, 30);

        usernameErrorLabel.setTextFill(Color.RED);
        usernameErrorLabel.setLayoutX(width / 2 - 250);
        usernameErrorLabel.setLayoutY(height / 2 - 100);
        usernameErrorLabel.setPrefSize(300, 30);

        emailErrorLabel.setLayoutX(width / 2 + 70);
        emailErrorLabel.setLayoutY(height / 2 + 40);
        emailErrorLabel.setTextFill(Color.RED);

        nicknameErrorLabel.setLayoutX(width / 2 + 70);
        nicknameErrorLabel.setLayoutY(height / 2 + 40);
        nicknameErrorLabel.setTextFill(Color.RED);

        sloganErrorLabel.setText(Errors.SLOGAN_EMPTY.getErrorText());
        sloganErrorLabel.setTextFill(Color.RED);
        sloganErrorLabel.setLayoutX(width / 2 - 250);
        sloganErrorLabel.setLayoutY(height / 2 + 110);
        sloganErrorLabel.setPrefSize(300, 30);
    }

    private Hyperlink getRandomPassword(double width, double height, PasswordField password, TextField visiblePassword) {
        Hyperlink randomPassword = new Hyperlink("random password");
        randomPassword.setLayoutX(width / 2 - 80);
        randomPassword.setLayoutY(height / 2 - 28);
        randomPassword.setPrefSize(150, 30);
        randomPassword.setOnMouseClicked(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Random Password");
            alert.setHeaderText("Do you want to use this password?");
            String randomPasswordText = RegisterMenuController.generateRandomPassword();
            alert.setContentText(randomPasswordText);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                registerPassword = randomPasswordText;
                password.setText(randomPasswordText);
                visiblePassword.setText(randomPasswordText);
            }
        });
        return randomPassword;
    }

    private CheckBox getShowPassword(Pane pane, double width, double height, PasswordField password, TextField visiblePassword) {
        CheckBox showPassword = new CheckBox("Show Password");
        showPassword.setLayoutX(width / 2 + 70);
        showPassword.setLayoutY(height / 2 - 40);
        showPassword.setPrefSize(20, 20);
        showPassword.setOnMouseClicked(mouseEvent -> {
            if (showPassword.isSelected()) {
                visiblePassword.setText(password.getText());
                pane.getChildren().remove(password);
                pane.getChildren().add(visiblePassword);
            } else {
                password.setText(visiblePassword.getText());
                pane.getChildren().remove(visiblePassword);
                pane.getChildren().add(password);
            }
        });
        return showPassword;
    }

    public static void setBackGround(Pane pane) {
        Image image = new Image(Images.BACK_GROUND4.getAddress());
        BackgroundImage backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        pane.setBackground(background);
    }

    private static CheckBox getSloganCheckBox(double width, double height) {
        CheckBox wantSlogan = new CheckBox();
        wantSlogan.setLayoutX(width / 2 + 70);
        wantSlogan.setLayoutY(height / 2 + 80);
        wantSlogan.setPrefSize(20, 20);
        return wantSlogan;
    }

    private void setRegisterNickname(double width, double height, TextField nickname) {
        nickname.setOnMouseClicked(event -> {
            nicknameErrorLabel.setText("");
            nicknameErrorLabel.setLayoutX(width / 2 + 70);
            nicknameErrorLabel.setLayoutY(height / 2 + 40);
            nicknameErrorLabel.setTextFill(Color.RED);
        });
        nickname.textProperty().addListener((observable, oldValue, newValue) -> {
            registerNickname = newValue;
        });
    }

    private void setRegisterEmail(double width, double height, TextField email) {
        email.setOnMouseClicked(event -> {
            emailErrorLabel.setText("");
            emailErrorLabel.setLayoutX(width / 2 + 70);
            emailErrorLabel.setLayoutY(height / 2 + 40);
            emailErrorLabel.setTextFill(Color.RED);
        });
        email.textProperty().addListener((observable, oldValue, newValue) -> {
            registerEmail = newValue;
        });
    }

    private Button submitButton(Pane pane) {
        Button submit = new Button("submit");
        submit.setLayoutX(pane.getPrefWidth() / 2 - 20);
        submit.setLayoutY(pane.getPrefHeight() / 2 + 120);
        submit.setOnMouseClicked(event -> {
            //todo: handle errors
            if (isUsernameOkay && isPasswordOkay) {
                if (registerNickname.equals("")) {
                    nicknameErrorLabel.setText("Nickname can't be empty");
                    return;
                } else if (registerEmail.equals("")) {
                    emailErrorLabel.setText("Email can't be empty");
                    return;
                } else {
                    nicknameErrorLabel.setText("");
                    emailErrorLabel.setText("");
                }
                String output = controller.register(registerUsername, registerPassword, registerPassword, registerEmail, registerNickname, registerSlogan);
                if (output.equals("player with this email already exists!")) {
                    emailErrorLabel.setText("player with this email already exists!");
                    return;
                } else if (output.equals("email format is incorrect!")) {
                    emailErrorLabel.setText("email format is incorrect!");
                    return;
                } else if (output.equals("register successfully!")) {
                    //todo: security question
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("register successfully!");
                    alert.setContentText("now you should answer the security question");
                    alert.showAndWait();
                    if (alert.getResult() == ButtonType.OK) {
                        try {
                            SecurityQuestionMenu.setUser(RegisterMenuController.getUserToRegister());
                            new SecurityQuestionMenu().start(EnterMenu.getStage());
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please fix the errors");
                alert.show();
            }

        });
        pane.getChildren().add(submit);
        return submit;
    }

    private Button resetButton(Pane pane, TextField username, PasswordField password, TextField email, TextField nickname) {
        Button reset = new Button("reset");
        reset.setLayoutX(pane.getPrefWidth() / 2 - 80);
        reset.setLayoutY(pane.getPrefHeight() / 2 + 120);
        reset.setOnMouseClicked(event -> {
            username.setText("");
            password.setText("");
            email.setText("");
            nickname.setText("");
            if (sloganField != null)
                sloganField.setText("");
        });
        pane.getChildren().add(reset);
        return reset;
    }

    private Label getSloganLabel(Pane pane, double width, double height, CheckBox wantSlogan, Button reset, Button submit) {
        Label label = new Label("Do you want to have a slogan?");
        label.setLayoutX(width / 2 - 100);
        label.setLayoutY(height / 2 + 80);
        wantSlogan.setOnMouseClicked(event -> {
            if (wantSlogan.isSelected()) {
                sloganField = sloganField(width, height, pane, reset, submit);
                pane.getChildren().add(sloganField);
            } else {
                reset.setLayoutY(height / 2 + 120);
                submit.setLayoutY(height / 2 + 120);
                sloganErrorLabel.setText("");
                pane.getChildren().remove(randomSlogan);
                pane.getChildren().remove(famousSlogans);
                famousSlogans.setSelected(false);
                if (pane.getChildren().contains(slogans)) {
                    pane.getChildren().remove(slogans);
                }
                pane.getChildren().remove(pane.getChildren().size() - 1);
            }
        });
        return label;
    }

    private TextField sloganField(double width, double height, Pane pane, Button reset, Button submit) {
        TextField slogan = new TextField();
        reset.setLayoutY(height / 2 + 220);
        submit.setLayoutY(height / 2 + 220);
        slogan.setPromptText("Slogan");
        slogan.setLayoutX(width / 2 - 100);
        slogan.setLayoutY(height / 2 + 110);
        slogan.setPrefSize(200, 30);
        slogan.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("")) {
                registerSlogan = null;
                sloganErrorLabel.setText(Errors.SLOGAN_EMPTY.getErrorText());
            } else {
                sloganErrorLabel.setText("");
                registerSlogan = newValue;
            }
        });
        randomSlogan.setLayoutX(width / 2 + 110);
        randomSlogan.setLayoutY(height / 2 + 110);
        randomSlogan.setPrefSize(120, 30);
        randomSlogan.setOnMouseClicked(event -> {
            String random = RegisterMenuController.generateRandomSlogan();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Random Slogan");
            alert.setContentText("Do you want to use this slogan?\n" + random);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                slogan.setText(random);
                registerSlogan = random;
            }
        });
        famousSlogans.setPrefSize(150, 30);
        famousSlogans.setLayoutX(width / 2 + 150);
        famousSlogans.setLayoutY(height / 2 + 150);
        famousSlogans.setOnMouseClicked(event -> {
            if (famousSlogans.isSelected()) {
                String famous = RegisterMenuController.getFamousSlogans();
                slogans.setText(famous);
                slogans.setLayoutX(width / 2 + 250);
                slogans.setLayoutY(height / 2 + 100);
                pane.getChildren().add(slogans);
            } else {
                pane.getChildren().remove(slogans);
            }
        });
        if (pane.getChildren().contains(sloganErrorLabel)) {
            pane.getChildren().remove(sloganErrorLabel);
        }
        if (pane.getChildren().contains(randomSlogan)) {
            pane.getChildren().remove(randomSlogan);
        }
        if (pane.getChildren().contains(famousSlogans)) {
            pane.getChildren().remove(famousSlogans);
        }
        pane.getChildren().addAll(sloganErrorLabel, randomSlogan, famousSlogans);
        return slogan;
    }

    private void usernameValidate(String newValue) {//todo: fix this
        if (newValue.equals("")) {
            usernameErrorLabel.setText(Errors.USERNAME_EMPTY.getErrorText());
        } else if (!RegisterMenuController.isCorrectUsername(newValue)) {
            usernameErrorLabel.setText(Errors.USERNAME_FORMAT_ERROR.getErrorText());
        }
//        else if (RegisterMenuController.isUsernameUsed(newValue)) {
//            usernameErrorLabel.setText(Errors.USERNAME_EXIST.getErrorText());
//        }
        else {
            usernameErrorLabel.setText("");
            registerUsername = newValue;
            isUsernameOkay = true;
        }
    }

    private void passwordValidate(String newValue) {
        if (!RegisterMenuController.isPasswordWeak(newValue).equals("true")) {
            passwordErrorLabel.setText(RegisterMenuController.isPasswordWeak(newValue));
            passwordErrorLabel.setTextFill(Color.RED);
        } else {
            passwordErrorLabel.setText("You're good to go");
            passwordErrorLabel.setTextFill(Color.GREEN);
            registerPassword = newValue;
            isPasswordOkay = true;
        }
    }

    private TextField usernameField(double width, double height) {
        TextField username = new TextField();
        username.setPromptText("Username");
        username.setLayoutX(width / 2 - 100);
        username.setLayoutY(height / 2 - 100);
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            usernameValidate(newValue);
        });
        return username;
    }

    private PasswordField passwordField(double width, double height, Pane pane) {
        PasswordField password = new PasswordField();
        password.setPromptText("Password");
        password.setLayoutX(width / 2 - 100);
        password.setLayoutY(height / 2 - 50);
        password.textProperty().addListener((observable, oldValue, newValue) -> {
            passwordValidate(newValue);
        });
        return password;
    }

    private TextField passwordFieldAsTextField(double width, double height, Pane pane) {
        TextField password = new TextField();
        password.setPromptText("Password");
        password.setLayoutX(width / 2 - 100);
        password.setLayoutY(height / 2 - 50);
        password.textProperty().addListener((observable, oldValue, newValue) -> {
            passwordValidate(newValue);
        });

        return password;
    }

    private TextField emailField(double width, double height) {
        TextField email = new TextField();
        email.setPromptText("Email");
        email.setLayoutX(width / 2 - 100);
        email.setLayoutY(height / 2);
        return email;
    }

    private TextField nicknameField(double width, double height) {
        TextField nickname = new TextField();
        nickname.setPromptText("Nickname");
        nickname.setLayoutX(width / 2 - 100);
        nickname.setLayoutY(height / 2 + 50);
        return nickname;
    }

}