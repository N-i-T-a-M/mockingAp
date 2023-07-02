package view;

import controller.RegisterMenuController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Captcha;
import model.User;
import model.UserDatabase;

import java.awt.*;

public class SecurityQuestionMenu extends Application {
    private static User user;
    private String registerAnswer;

    public static void setUser(User user) {
        SecurityQuestionMenu.user = user;
    }


    @Override
    public void start(Stage stage) throws Exception {
        VBox vBox = new VBox();
        Pane pane = new Pane(vBox);
        RegisterMenu.setBackGround(pane);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        vBox.setLayoutX(width / 2 - 200);
        vBox.setLayoutY(height / 2 - 200);
        pane.setPrefSize(width, height);
        getBack(stage, pane);
        ComboBox<String> comoBox = getComboBox(vBox);
        vBox.getChildren().add(comoBox);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    private ComboBox<String> getComboBox(VBox vBox) {
        ComboBox<String> comoBox = new ComboBox();
        for (int i = 0; i < 3; i++) {
            comoBox.getItems().add(UserDatabase.getQuestions().get(i));
        }
        comoBox.setValue(UserDatabase.getQuestions().get(0));
        int number = 0;
        comoBox.setOnMouseClicked(event -> {
            while (vBox.getChildren().size() > 1) {
                vBox.getChildren().remove(1);
            }
            TextField answer = new TextField();
            answer.setPromptText("Answer");
            Button submit = new Button("Submit");
            Button reset = new Button("Reset");
            HBox hBox = new HBox();
            hBox.getChildren().addAll(submit, reset);
            vBox.getChildren().addAll(answer, hBox);
            reset.setOnMouseClicked(event1 -> {
                answer.setText("");
            });
            submit.setOnMouseClicked(event1 -> {
                if (!answer.getText().equals("")) {
                    CaptchaMenu captchaMenu = new CaptchaMenu();
                    captchaMenu.setRegisteringUser(user);
                    captchaMenu.setUserAnswer(answer.getText());
                    captchaMenu.setUserQuestionNumber(comoBox.getSelectionModel().getSelectedIndex() + 1);
                    try {
                        captchaMenu.start(EnterMenu.getStage());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        });
        return comoBox;
    }

    private static void getBack(Stage stage, Pane pane) {
        Button back = new Button("Back");
        back.setOnMouseClicked(event -> {
            try {
                new EnterMenu().start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        pane.getChildren().add(back);
    }

}
