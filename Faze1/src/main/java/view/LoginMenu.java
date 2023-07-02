package view;

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
import model.UserDatabase;

import java.awt.*;

public class LoginMenu extends Application {
    private Label usernameError = new Label();

    {
        usernameError.setTextFill(javafx.scene.paint.Color.RED);
    }

    private Label passwordError = new Label();

    {
        passwordError.setTextFill(javafx.scene.paint.Color.RED);
    }

    private Label questionError = new Label();

    {
        questionError.setTextFill(javafx.scene.paint.Color.RED);
    }

    @Override
    public void start(Stage stage) throws Exception {
        UserDatabase.setAreWeLoggingIn(true);
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
        vBox.getChildren().addAll(username, password);
        vBox.setLayoutX(width / 2 - 100);
        vBox.setLayoutY(height / 2 - 200);
        vBox.setSpacing(20);
        vBox1.setLayoutX(width / 2 - 250);
        vBox1.setLayoutY(height / 2 - 200);
        vBox1.setSpacing(20);
        VBox vBox2 = new VBox(questionError);
        vBox2.setLayoutX(width / 2 - 100);
        vBox2.setLayoutY(height / 2 - 200);
        pane.getChildren().add(vBox2);
        Button enter = getEnter(username, password);
        Button forgotPassword = getForgotPassword(vBox, username, vBox2);
        vBox.getChildren().addAll(forgotPassword, enter);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    private Button getForgotPassword(VBox vBox, TextField username, VBox vBox2) {//todo check
        Button forgotPassword = new Button("Forgot Password");
        forgotPassword.setOnMouseClicked(event -> {
            usernameError.setText("");
            passwordError.setText("");
            questionError.setText("");
            if (username.getText().equals("")) {
                usernameError.setText("Username can't be empty");
            } else if (!RegisterMenuController.isUsernameUsed(username.getText())) {
                usernameError.setText("Username doesn't exist");
            } else {
                usernameError.setText("");
                vBox.getChildren().clear();
                Text text = new Text("answer the question\n" + UserDatabase.getUserByUsername(username.getText()).getQuestion());
                text.setFill(Color.YELLOW);
                TextField answer = new TextField();
                Button submit = getSubmit(username, vBox2, answer);
                vBox2.getChildren().addAll(text, answer, submit);
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
            } else if (!UserDatabase.getUserByUsername(username.getText()).getAnswer().equals(answer.getText())) {
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
            if (confirm.getText().equals("")) {
                passwordError.setText("Password can't be empty");
            } else if (!confirm.getText().equals(newPassword)) {
                passwordError.setText("Passwords don't match");
            } else {
                passwordError.setText("");
                UserDatabase.getUserByUsername(username.getText()).setPassword(newPassword);
                vBox2.getChildren().clear();
                UserDatabase.setCurrentUser(UserDatabase.getUserByUsername(username.getText()));
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Password Changed");
                alert1.setHeaderText("Your password has been changed successfully");
                alert1.showAndWait();
                try {
                    UserDatabase.setCurrentUser(UserDatabase.getUserByUsername(username.getText()));
                    new EnterMenu().start(EnterMenu.getStage());
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
            } else if (UserDatabase.getUserByUsername(username.getText()) == null) {
                passwordError.setText("");
                usernameError.setText("Username doesn't exist");
            } else {
                try {
                    String hashedPass = UserDatabase.hashPassword(password.getText(), UserDatabase.getUserByUsername(username.getText()).getSalt());
                    if (hashedPass.equals(UserDatabase.getUserByUsername(username.getText()).getPassword())) {
                        CaptchaMenu captchaMenu = new CaptchaMenu();
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
//    private int numberOfWrongPasswords = 0;
//
//    public void run(Scanner scanner) throws NoSuchAlgorithmException {
//        LoginMenuController controller = new LoginMenuController();
//        String input;
//        Matcher userLogin, forgotMyPassword;
//        while (true) {
//            input = scanner.nextLine();
//            userLogin = LoginMenuCommands.getMatcher(input, LoginMenuCommands.USER_LOGIN);
//            forgotMyPassword = LoginMenuCommands.getMatcher(input, LoginMenuCommands.FORGOT_MY_PASSWORD);
//            if (userLogin.find()) {
//                if (UserLogin(scanner, controller, userLogin)) return;
//            } else if (forgotMyPassword.find()) {
//                if (ForgotMyPassword(scanner, controller, input, forgotMyPassword)) return;
//            } else if (input.equalsIgnoreCase("back")) {
//                return;
//            } else {
//                System.out.println("Invalid command!");
//            }
//        }
//    }
//
//    private boolean UserLogin(Scanner scanner, LoginMenuController controller, Matcher userLogin) throws NoSuchAlgorithmException {
//        if (userLogin.group("username").isEmpty()) {
//            System.out.println("Username can't be empty");
//            return false;
//        }
//        if (userLogin.group("password").isEmpty()) {
//            System.out.println("Password can't be empty");
//            return false;
//        }
//        String username = userLogin.group("username");
//        String password = userLogin.group("password");
//        boolean stayLoggedIn = userLogin.group("stay") != null;
//        String output = controller.login(username, password);
//        System.out.println(output);
//        if (output.equals("Username and password didn't match!")) {
//            return delay();
//        } else if (output.equals("now you must answer the captcha")) {
//            while (true) {
//                if (showCaptcha(scanner, controller, username, stayLoggedIn)) return true;
//            }
//        }
//        return false;
//    }
//
//    private boolean delay() {
//        numberOfWrongPasswords++;
//        if (numberOfWrongPasswords <= 3) {
//            System.out.println("Please wait " + numberOfWrongPasswords * 5 + " seconds.");
//            for (int i = 0; i < 5 * numberOfWrongPasswords; i++) {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    Thread.currentThread().interrupt();
//                }
//                System.out.println(5 * numberOfWrongPasswords - i);
//            }
//        } else {
//            System.out.println("Too many attempts, Please try again later");
//            return true;
//        }
//        return false;
//    }
//
//    private static boolean showCaptcha(Scanner scanner, LoginMenuController controller, String username, boolean stayLoggedIn) throws NoSuchAlgorithmException {
//        String output;
//        String input;
//        Captcha captcha = new Captcha();
//        for (int i = 0; i < 8; i++) {
//            for (int j = 0; j < captcha.getCreatedCaptcha().length; j++) {
//                System.out.print(captcha.getCreatedCaptcha()[j][i]);
//            }
//            System.out.println();
//        }
//        System.out.println("Please enter the captcha");
//        input = scanner.nextLine();
//        if (input.isEmpty()) {
//            System.out.println("captcha can't be empty");
//            return false;
//        }
//        output = controller.isCaptchaCorrect(captcha, input);
//        System.out.println(output);
//        if (output.equals("user logged in successfully!")) {
//            UserDatabase.setCurrentUser(UserDatabase.getUserByUsername(username));
//            UserDatabase.getUserByUsername(username).setStayLoggedIn(stayLoggedIn);
//            UserDatabase.saveUsers();
//            MainMenu menu = new MainMenu();
//            menu.run(scanner);
//            return true;
//        }
//        return false;
//    }
//
//    private static boolean ForgotMyPassword(Scanner scanner, LoginMenuController controller, String input, Matcher forgotMyPassword) throws NoSuchAlgorithmException {
//        if (forgotMyPassword.group("username").isEmpty()) {
//            System.out.println("Username can't be empty");
//            return false;
//        }
//        String output = controller.checkUsername(forgotMyPassword.group("username"));
//        System.out.println(output);
//        if (output.equals("The username format is incorrect.") || output.equals("User with this username doesn't exist.")) {
//            return false;
//        }
//        if (output.equals("Please answer your security question:\n" + UserDatabase.getUserByUsername(forgotMyPassword.group("username")).getQuestion())) {
//            while (true) {
//                input = scanner.nextLine();
//                output = controller.checkAnswer(forgotMyPassword.group("username"), input);
//                System.out.println(output);
//                if (output.equals("Correct. Please set your new password.")) {
//                    Matcher newPassword;
//                    while (true) {
//                        input = scanner.nextLine();
//                        newPassword = LoginMenuCommands.getMatcher(input, LoginMenuCommands.NEW_PASSWORD);
//                        if (newPassword.find()) {
//                            if (newPassword.group("password").isEmpty()) {
//                                System.out.println("Password can't be empty");
//                                continue;
//                            }
//                            if (newPassword.group("passwordRepeat").isEmpty()) {
//                                System.out.println("Password can't be empty");
//                                continue;
//                            }
//                            output = controller.setNewPassword(forgotMyPassword.group("username"), newPassword.group("password"), newPassword.group("passwordRepeat"));
//                            System.out.println(output);
//                            if (hasPasswordChanged(output)) {
//                                while (true) {
//                                    showCaptcha(scanner, controller, forgotMyPassword.group("username"), false);
//                                }
//                            }
//                        } else {
//                            System.out.println("Invalid command!");
//                        }
//                    }
//                }
//            }
//        } else if (input.equals("back")) {
//            return true;
//        } else {
//            System.out.println("Invalid command!");
//        }
//        return false;
//    }
//
//    private static boolean hasPasswordChanged(String output) {
//        return output.contains("password changed successfully.");
//    }
