package practices;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.util.concurrent.atomic.AtomicReference;

public class FileDropDemo extends Application {

    @Override
    public void start(Stage primaryStage) {
        // create a label to display dropped file names
        Label label = new Label("Drop a file here");
        label.setFont(Font.font(24));

        // enable drag-and-drop on the label
        label.setOnDragOver(event -> {
            if (event.getDragboard().hasFiles()) {
                event.acceptTransferModes(javafx.scene.input.TransferMode.COPY);
            }
            event.consume();
        });
        // handle the dropped files
        label.setOnDragDropped(event -> {
            javafx.scene.input.Dragboard dragboard = event.getDragboard();
            if (dragboard.hasFiles()) {
                File file = dragboard.getFiles().get(0);
                label.setText("Dropped file: " + file.getName());
            }
            event.setDropCompleted(true);
            event.consume();
        });

        // create a layout and add the label to it
        StackPane root = new StackPane();
        root.getChildren().add(label);
        // create a scene and set it on the stage
        Scene scene = new Scene(root, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}