module Faze1 {
    requires com.google.gson;
    requires javafx.graphics;
    requires java.desktop;
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.media;
    exports view;
    opens model to com.google.gson;
    exports controller;
    opens controller to javafx.fxml;
    opens view to com.google.gson, javafx.fxml;
    exports Enums;
    opens Enums to com.google.gson, javafx.fxml;
//    exports view.shop;
//    opens view.shop to com.google.gson, javafx.fxml;
//    exports view.trade;
//    opens view.trade to com.google.gson, javafx.fxml;
//    exports view.lobby;
//    opens view.lobby to com.google.gson, javafx.fxml;
    exports model.chat;
    opens model.chat to com.google.gson, javafx.fxml;

}