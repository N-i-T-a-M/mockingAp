package view.trade;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MessagePopup {
    private static String message = "";

    public static String getMessage() {
        return message;
    }

    public static void display(String title, String buttonText) {
        Stage popupWindow = new Stage();
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setTitle(title);

        Label label = new Label("Write your message:");
        TextArea messageArea = new TextArea();

        Button sendButton = new Button(buttonText);
        sendButton.setOnAction(e -> {
            // Handle sending the message here
            message = messageArea.getText();
            System.out.println("Message: " + message);
            popupWindow.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, messageArea, sendButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 300, 200);
        popupWindow.setScene(scene);
        popupWindow.showAndWait();
    }
}