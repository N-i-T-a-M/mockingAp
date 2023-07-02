package view.shop;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.MainMenu;
import view.trade.TradeMenu;

import java.awt.*;

public class ShopMenu extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Pane root = new Pane();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        root.setPrefSize(width, height);
        VBox vBox = new VBox();
        vBox.setLayoutX(5);
        vBox.setLayoutY(5);
        Button back = getBack(stage);
        vBox.getChildren().add(back);
        Button food = getFood(stage);
        Button resources = getResources(stage);
        Button weapons = getWeapons(stage);
        Button defenseWeapons = getDefenseWeapons(stage);
        Button trade = getTrade(stage);
        vBox.getChildren().addAll(food, resources, weapons, defenseWeapons, trade);
        vBox.setSpacing(20);
        root.getChildren().add(vBox);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private Button getTrade(Stage stage) {
        Button trade = new Button("Trade");
        trade.setOnMouseClicked(event -> {
            try {
                new TradeMenu().start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return trade;
    }

    private static Button getDefenseWeapons(Stage stage) {
        Button defenseWeapons = new Button("Defense Weapons");
        defenseWeapons.setOnMouseClicked(event -> {
            try {
                new PropertyMenu("Defence Weapons").start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return defenseWeapons;
    }


    private static Button getWeapons(Stage stage) {
        Button weapons = new Button("Weapons");
        weapons.setOnMouseClicked(event -> {
            try {
                new PropertyMenu("weapons").start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return weapons;
    }


    private static Button getResources(Stage stage) {
        Button resources = new Button("Resources");
        resources.setOnMouseClicked(event -> {
            try {
                new PropertyMenu("resources").start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return resources;
    }


    private static Button getFood(Stage stage) {
        Button food = new Button("Food");
        food.setOnMouseClicked(event -> {
            try {
                new PropertyMenu("food").start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return food;
    }


    private static Button getBack(Stage stage) {//todo
        Button back = new Button("Back");
        back.setOnMouseClicked(event -> {
            MainMenu mainMenu = new MainMenu();
            try {
                mainMenu.start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return back;
    }
}
//    public void run(Scanner scanner, Game game) {
//        System.out.println("welcome to shop menu");
//        String input;
//        Matcher showPriceList, buyAndSell;
//        ShopMenuController controller = new ShopMenuController(game);
//        while (true) {
//            input = scanner.nextLine();
//            showPriceList = ShopMenuCommands.getMatcher(input, ShopMenuCommands.SHOW_PRICE_LIST);
//            buyAndSell = ShopMenuCommands.getMatcher(input, ShopMenuCommands.BUY_AND_SELL);
//            if (showPriceList.find()) {
//                System.out.print(controller.showPriceList());
//            } else if (buyAndSell.find()) {
//                System.out.println(controller.buyOrSell(buyAndSell.group("action"), buyAndSell.group("name"), buyAndSell.group("number")));
//            } else if (input.equals("back")) {
//                return;
//            } else {
//                System.out.println("Invalid command!");
//            }
//        }
//    }
