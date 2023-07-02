package view.trade;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.User;
import view.MainMenu;
import view.shop.PropertyMenu;

import java.awt.*;


public class TradeWithPlayerX extends Application {
    private static User toTradeWith;
    private static Text property = PropertyMenu.getProperty();
    private static int amount;
    private static Text count = new Text();

    public static void setToTradeWith(User toTradeWith) {
        TradeWithPlayerX.toTradeWith = toTradeWith;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Pane root = new Pane();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        root.setPrefSize(width, height);
        Button back = getBack(stage);
        root.getChildren().add(back);
        GridPane gridPane = getGridPane();
        gridPane.setLayoutX(50);
        gridPane.setLayoutY(50);
        root.getChildren().add(gridPane);
        root.getChildren().add(property);
        setCount(width);
        root.getChildren().add(count);
        ImageView add = getAdd(width, height);
        ImageView remove = getRemove(width, height);
        Button request = getRequest(width, height);
        Button donate = getDonate(width, height);
        HBox hBox = getBox(width, height, request, donate);
        root.getChildren().addAll(add, remove, hBox);
        setChosenProperty(width);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private static HBox getBox(double width, double height, Button request, Button donate) {
        HBox hBox = new HBox(request, donate);
        hBox.setLayoutX(width / 2 - 100);
        hBox.setLayoutY(height - 100);
        hBox.setSpacing(20);
        return hBox;
    }

    private Button getDonate(double width, double length) {
        Button donate = new Button("Donate");
        donate.setPrefSize(100, 50);
        donate.setOnMouseClicked(event -> {
            if (amount == 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You should choose something");
                alert.showAndWait();
            } else {
                MessagePopup.display("Send a message", "Send");
                //todo donate to someone
            }
        });
        return donate;
    }

    private static Button getRequest(double width, double height) {
        Button request = new Button("Request");
        request.setPrefSize(100, 50);
        request.setOnMouseClicked(event -> {
            if (amount == 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You should choose something");
                alert.showAndWait();
            } else {
                MessagePopup.display("Send a message", "Send");
                //todo request a trade
            }
        });
        return request;
    }

    private static void setCount(double width) {
        count.textProperty().addListener((observable, oldValue, newValue) -> {
            count.setText(String.valueOf(amount));
        });
        count.setLayoutX(width / 2 + 200);
        count.setLayoutY(150);
    }

    private static void setChosenProperty(double width) {
        property.setLayoutX(width / 2 + 100);
        property.setLayoutY(50);
        property.setFont(javafx.scene.text.Font.font(50));
        property.setFill(Color.RED);
        property.textProperty().addListener((observable, oldValue, newValue) -> {
            amount = 0;
            property.setText(PropertyMenu.getProperty().getText());
            count.textProperty().set(0 + "");
        });
    }

    private static ImageView getAdd(double width, double height) {
        ImageView add = new ImageView(TradeWithPlayerX.class.getResource("/Avatars/plus.png").toExternalForm());
        add.setOnMouseClicked(event -> {
            if (!property.getText().equals("")) {
                amount++;
                count.setText(String.valueOf(amount));
            }
        });
        add.setLayoutX(width / 2 + 100);
        add.setLayoutY(100);
        add.setFitWidth(50);
        add.setFitHeight(50);
        return add;
    }

    private static ImageView getRemove(double width, double height) {
        ImageView remove = new ImageView(TradeWithPlayerX.class.getResource("/Avatars/minus.png").toExternalForm());
        remove.setOnMouseClicked(event -> {
            if (!property.getText().equals("")) {
                amount--;
                if (amount < 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("You can't have negative amount of this property");
                    alert.showAndWait();
                    amount = 0;
                } else {
                    count.setText(String.valueOf(amount));
                }
            }
        });
        remove.setLayoutX(width / 2 + 100);
        remove.setLayoutY(200);
        remove.setFitWidth(50);
        remove.setFitHeight(50);
        return remove;
    }

    private static Button getBack(Stage stage) {//todo
        javafx.scene.control.Button back = new Button("Back");
        back.setOnMouseClicked(event -> {
            MainMenu mainMenu = new MainMenu();
            try {
                new TradeMenu().start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return back;
    }

    private static GridPane getGridPane() {
        GridPane gridPane = new GridPane();
        PropertyMenu.addAle(gridPane, 0, 0);
        PropertyMenu.addApples(gridPane, 1, 0);
        PropertyMenu.addBread(gridPane, 2, 0);
        PropertyMenu.addCheese(gridPane, 3, 0);
        PropertyMenu.addBarley(gridPane, 4, 0);
        PropertyMenu.addBow(gridPane, 5, 0);
        PropertyMenu.addCrossBow(gridPane, 0, 1);
        PropertyMenu.addFlour(gridPane, 1, 1);
        PropertyMenu.addIron(gridPane, 2, 1);
        PropertyMenu.addMeat(gridPane, 3, 1);
        PropertyMenu.addLeatherArmour(gridPane, 4, 1);
        PropertyMenu.addMetalArmour(gridPane, 5, 1);
        PropertyMenu.addMace(gridPane, 0, 2);
        PropertyMenu.addPike(gridPane, 1, 2);
        PropertyMenu.addWood(gridPane, 2, 2);
        PropertyMenu.addSword(gridPane, 3, 2);
        PropertyMenu.addStone(gridPane, 4, 2);
        PropertyMenu.addWheat(gridPane, 5, 2);
        PropertyMenu.addPetroleum(gridPane, 0, 3);
        PropertyMenu.addSpear(gridPane, 1, 3);
        PropertyMenu.addPitch(gridPane, 2, 3);
        return gridPane;
    }
}
