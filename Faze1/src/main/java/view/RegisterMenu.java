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
        } else if (RegisterMenuController.isUsernameUsed(newValue)) {
            usernameErrorLabel.setText(Errors.USERNAME_EXIST.getErrorText());
        } else {
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
//    public void run(Scanner scanner) {
//        System.out.println("Welcome to register menu!");
//        String input;
//        Matcher userCreate;
//        RegisterMenuController controller = new RegisterMenuController();
//        while (true) {
//            input = scanner.nextLine();
//            userCreate = RegisterMenuCommands.getMatcher(input, RegisterMenuCommands.USER_CREATE);
//
//            if (userCreate.find()) {
//                if (UserCreate(scanner, userCreate, controller)) return;
//            } else if (input.equalsIgnoreCase("back")) {
//                return;
//            } else {
//                System.out.println("Invalid command!");
//            }
//        }
//    }
//
//    private static boolean UserCreate(Scanner scanner, Matcher userCreate, RegisterMenuController controller) {
//        boolean chooseSuggestedUsername = false;
//        if (!checkEmptyFields(userCreate)) {
//            return false;
//        }
//        String username = userCreate.group("username");
//        String password;
//        String passwordRepeat;
//        String email = userCreate.group("email");
//        String nickname = userCreate.group("nickname");
//        String slogan = "";
//        if (userCreate.group("slogan") != null) {
//            slogan = userCreate.group("slogan");
//            if (slogan.equals("random")) {
//                slogan = RegisterMenuController.generateRandomSlogan();
//            }
//        }
//        if (!userCreate.group("password").equalsIgnoreCase("random")) {
//            password = userCreate.group("password");
//            passwordRepeat = userCreate.group("passwordRepeat");
//        } else {
//            password = RegisterMenuController.generateRandomPassword();
//            passwordRepeat = password;
//        }
//        String output = controller.register(username, password, passwordRepeat, email, nickname, slogan);
//        System.out.println(output);
//        if (output.equals("A player with this username already exist! but you might find these interesting:")) {
//            chooseSuggestedUsername = isChooseSuggestedUsername(scanner, controller, chooseSuggestedUsername, username, password, passwordRepeat, email, nickname, slogan);
//        }
//        if (chooseSuggestedUsername) {
//            output = "register successfully!";
//        }
//        if (output.equals("register successfully!")) {
//            return register(scanner, userCreate, controller, password, slogan);
//        }
//        return false;
//    }
//
//    private static boolean checkEmptyFields(Matcher userCreate) {
//        if (userCreate.group("username").isEmpty()) {
//            System.out.println("Username can't be empty");
//            return false;
//        }
//        if (userCreate.group("password").isEmpty()) {
//            System.out.println("Password can't be empty");
//            return false;
//        }
//        if (userCreate.group("passwordRepeat").isEmpty()) {
//            System.out.println("Password confirmation can't be empty");
//            return false;
//        }
//        if (userCreate.group("email").isEmpty()) {
//            System.out.println("Email can't be empty");
//            return false;
//        }
//        if (userCreate.group("nickname").isEmpty()) {
//            System.out.println("Nickname can't be empty");
//            return false;
//        }
//        return true;
//    }
//
//    private static boolean register(Scanner scanner, Matcher userCreate, RegisterMenuController controller, String password, String slogan) {
//        Matcher questionPick;
//        String input;
//        if (userCreate.group("password").equalsIgnoreCase("random")) {
//            System.out.println("This is your password: " + password);
//            System.out.println("Please confirm it: ");
//            while (true) {
//                input = scanner.nextLine();
//                if (!password.equals(input)) {
//                    System.out.println("The confirmation doesn't match the password, please try again");
//                } else {
//                    System.out.println("Password confirmed successfully");
//                    break;
//                }
//            }
//        }
//        if (userCreate.group("slogan") != null && userCreate.group("slogan").equals("random")) {
//            System.out.println("This is your slogan:");
//            System.out.println(slogan);
//        }
//        System.out.print(controller.showQuestions());
//        while (true) {
//            input = scanner.nextLine();
//            questionPick = RegisterMenuCommands.getMatcher(input, RegisterMenuCommands.QUESTION_PICK);
//            if (questionPick.find()) {
//                if (QuestionPick(scanner, controller, questionPick)) return true;
//            } else if (input.equals("back")) {
//                break;
//            } else {
//                System.out.println("Please answer the question");
//            }
//        }
//        return false;
//    }
//
//    private static boolean QuestionPick(Scanner scanner, RegisterMenuController controller, Matcher questionPick) {
//        if (questionPick.group("questionNumber").isEmpty()) {
//            System.out.println("Question number can't be empty");
//            return false;
//        }
//        if (questionPick.group("answer").isEmpty()) {
//            System.out.println("Answer can't be empty");
//            return false;
//        }
//        if (questionPick.group("answerConfirm").isEmpty()) {
//            System.out.println("Answer confirmation can't be empty");
//            return false;
//        }
//        int number;
//        try {
//            number = Integer.parseInt(questionPick.group("questionNumber"));
//        } catch (NumberFormatException e) {
//            System.out.println("Invalid question number");
//            return false;
//        }
//        String answer = questionPick.group("answer");
//        String answerConfirm = questionPick.group("answerConfirm");
//        String show = controller.securityQuestionErrors(number, answer, answerConfirm);
//        System.out.println(show);
//        if (show.equals("question and answer selected!")) {
//            while (true) {
//                if (showCaptcha(scanner, controller, number, answer)) return true;
//            }
//        }
//        return false;
//    }
//
//    private static boolean showCaptcha(Scanner scanner, RegisterMenuController controller, int number, String answer) {
//        String input;
//        Captcha captcha = new Captcha();
//        System.out.println("please enter the captcha to finish registering");
//        for (int i = 0; i < 8; i++) {
//            for (int j = 0; j < captcha.getCreatedCaptcha().length; j++) {
//                System.out.print(captcha.getCreatedCaptcha()[j][i]);
//            }
//            System.out.println();
//        }
//        input = scanner.nextLine();
//        int A = 0;
//        if (input.length() != captcha.getCreatedCaptcha().length) {
//            System.out.println("incorrect captcha, please try again");
//            return false;
//        }
//        for (int i = 0; i < captcha.getCreatedCaptcha().length; i++) {
//            if (input.charAt(i) - '0' != captcha.getNumbers()[i]) {
//                System.out.println("incorrect captcha, please try again");
//                A++;
//                break;
//            }
//        }
//        if (A == 0) {
//            System.out.println("Account created successfully");
//            controller.addUser(answer, number);
//            return true;
//        }
//        return false;
//    }
//
//    private static boolean isChooseSuggestedUsername(Scanner scanner, RegisterMenuController controller, boolean chooseSuggestedUsername, String username, String password, String passwordRepeat, String email, String nickname, String slogan) {
//        String input;
//        String[] toAdds = new String[3];
//        toAdds[0] = "king";
//        toAdds[1] = "007";
//        toAdds[2] = "haj";
//        for (int i = 0; i < 3; i++) {
//            System.out.println((i + 1) + ")" + toAdds[i] + username);
//        }
//        first:
//        while (true) {
//            System.out.println("please choose your favorite username.If you don't have one please type in anything else");
//            input = scanner.nextLine();
//            if (input.equals("1") || input.equals("2") || input.equals("3")) {
//                for (int i = 1; i < 4; i++) {
//                    if (Integer.parseInt(input) == i) {
//                        String answer = controller.register(toAdds[i - 1] + username, password, passwordRepeat, email, nickname, slogan);
//                        System.out.println(answer);
//                        if (answer.equals("register successfully!")) {
//                            chooseSuggestedUsername = true;
//                            System.out.println("This is your username: " + toAdds[i - 1] + username);
//                            break first;
//                        }
//                    }
//                }
//            }
//        }
//        return chooseSuggestedUsername;
//    }
