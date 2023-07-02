package view.trade;

import Commands.TradeMenuCommands;
import controller.TradeMenuController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Game;
import model.Kingdom;
import model.User;
import model.map.Map;
import view.MainMenu;
import view.shop.ShopMenu;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;

public class TradeMenu extends Application {
    private int playersCount = Game.getYetGame().getPlayers().size();
    @Override
    public void start(Stage stage) throws Exception {
        Pane root = new Pane();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        root.setPrefSize(width, height);
        Button back = getBack(stage);
        back.setLayoutX(5);
        back.setLayoutY(5);
        root.getChildren().add(back);
        Button newTrade = getNewTrade(stage,root);
        Button recentTrades = getRecentTrades(stage,root);
        HBox hBox = new HBox(newTrade,recentTrades);
        hBox.setLayoutX(width/2 - 100);
        hBox.setLayoutY(height/2 - 50);
        newTrade.setPrefSize(100,50);
        recentTrades.setPrefSize(100,50);
        hBox.setSpacing(20);
        root.getChildren().addAll(hBox);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private Button getRecentTrades(Stage stage,Pane pane) {
        Button recentTrades = new Button("Recent Trades");
        recentTrades.setOnMouseClicked(event-> {
            pane.getChildren().remove(1,2);

        });

        return recentTrades;
    }

    private Button getNewTrade(Stage stage,Pane pane) {
        Button newTrade = new Button("New Trade");
        newTrade.setOnMouseClicked(event -> {
           //ArrayList<Kingdom> players =  Game.getYetGame().getPlayers();
           pane.getChildren().remove(1,2);
            GridPane gridPane = new GridPane();
            for (int i = 0; i < playersCount; i++) {
                VBox box = new VBox();
                box.setPrefSize(100,100);
                box.setStyle("-fx-background-color: #000000");
                ImageView imageView = new ImageView(Game.getYetGame().getPlayers().get(i).getOwner().getAvatar());
                Text text = new Text(Game.getYetGame().getPlayers().get(i).getOwner().getUsername());
                box.getChildren().addAll(imageView,text);
                User user = Game.getYetGame().getPlayers().get(i).getOwner();
                imageView.setOnMouseClicked(event1->{
                    try {
                        TradeWithPlayerX.setToTradeWith(user);
                        new TradeWithPlayerX().start(stage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        });
        return newTrade;
    }

    private static Button getBack(Stage stage) {//todo
        javafx.scene.control.Button back = new Button("Back");
        back.setOnMouseClicked(event -> {
            MainMenu mainMenu = new MainMenu();
            try {
                new ShopMenu().start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return back;
    }
}
//    public void run(Scanner scanner, Game game) {
//        System.out.println("welcome to trade menu");
//        String input;
//        Matcher tradeList, trade, accept, tradeHistory;
//        TradeMenuController controller = new TradeMenuController(game);
//        while (true) {
//            input = scanner.nextLine();
//            tradeList = TradeMenuCommands.getMatcher(input, TradeMenuCommands.SHOW_ALL_TRADES);
//            tradeHistory = TradeMenuCommands.getMatcher(input, TradeMenuCommands.TRADE_HISTORY);
//            trade = TradeMenuCommands.getMatcher(input, TradeMenuCommands.TRADE);
//            accept = TradeMenuCommands.getMatcher(input, TradeMenuCommands.TRADE_ACCEPT);
//            if (tradeList.find()) {
//                System.out.print(controller.showAllTrades());
//            } else if (trade.find()) {
//                System.out.println(controller.trade(trade.group("type"), trade.group("amount"), trade.group("price"), trade.group("message"), trade.group("receiverName")));
//            } else if (accept.find()) {
//                System.out.println(controller.acceptTrade(accept.group("id"), accept.group("message")));
//            } else if (tradeHistory.find()) {
//                System.out.println(controller.showTradeHistory());
//            } else if (input.equals("back")) {
//                controller.deleteTrades();
//                return;
//            } else {
//                System.out.println("Invalid command!");
//            }
//        }
//    }
