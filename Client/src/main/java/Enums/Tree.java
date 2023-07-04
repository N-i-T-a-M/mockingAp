package Enums;

import javafx.scene.image.Image;
import model.map.Cell;

public enum Tree {
    DESERT_SHRUB("/images/shrub.png"),
    CHERRY("/images/cherry_palm.png"),
    OLIVE("/images/olive.png"),
    COCONUT("/images/coconut_palm.png"),
    DATE("/images/dates_palm.png");
    private String path;

    Tree(String path) {
        this.path = path;
    }

    public Image getImage() {
        return new javafx.scene.image.Image((Cell.class.getResource(path).toExternalForm()));
    }
}
