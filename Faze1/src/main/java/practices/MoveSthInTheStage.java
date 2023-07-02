package practices;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class MoveSthInTheStage extends Application {
    private Circle circle;
    private double offsetX;
    private double offsetY;
    @Override
    public void start(Stage stage) throws Exception {
        // create a circle
        circle = new Circle(50, 50, 20, Color.RED);

        // add event handlers for dragging
        circle.setOnMousePressed(event -> {
            offsetX = event.getX() - circle.getCenterX();
            offsetY = event.getY() - circle.getCenterY();
        });
        circle.setOnMouseDragged(event -> {
            circle.setCenterX(event.getX() - offsetX);
            circle.setCenterY(event.getY() - offsetY);
        });
        circle.setOnMouseReleased(event -> {
            // snap to grid when released
            double x = Math.round(circle.getCenterX() / 50) * 50;
            double y = Math.round(circle.getCenterY() / 50) * 50;
            circle.setCenterX(x);
            circle.setCenterY(y);
        });

        // create a layout and add the circle to it
        Pane root = new Pane();
        root.getChildren().add(circle);

        // create a scene and set it on the stage
        Scene scene = new Scene(root, 300, 300);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
