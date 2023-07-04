package Enums;

import java.awt.*;
import javafx.scene.image.Image;
import model.map.Cell;


public enum TextureType {
    EARTH(true, false, new Image(Cell.class.getResource("/images/ground.jpg").toExternalForm())),
    GRAVEL(true, false, new Image(Cell.class.getResource("/images/Mountain1.png").toExternalForm())),
    STONE_MINE(true, false, new Image(Cell.class.getResource("/images/Mountain1.png").toExternalForm())),
    ROCK_NORTH(false, false, new Image(Cell.class.getResource("/images/stone.jpg").toExternalForm())),
    ROCK_EAST(false, false, new Image(Cell.class.getResource("/images/stone.jpg").toExternalForm())),
    ROCK_WEST(false, false, new Image(Cell.class.getResource("/images/stone.jpg").toExternalForm())),
    ROCK_SOUTH(false, false, new Image(Cell.class.getResource("/images/stone.jpg").toExternalForm())),
    IRON_MINE(true, false, new Image(Cell.class.getResource("/images/ironGround.jpg").toExternalForm())),
    GRASS(true, false, new Image(Cell.class.getResource("/images/grass.jpg").toExternalForm())),
    MEADOW(true, false, new Image(Cell.class.getResource("/images/grassland2.jpg").toExternalForm())),
    DENSE_MEADOW(true, false, new Image(Cell.class.getResource("/images/grass.jpg").toExternalForm())),
    OIL(true, true, new Image(Cell.class.getResource("/images/ground.jpg").toExternalForm())),
    PLAIN(true, true, new Image(Cell.class.getResource("/images/plain.jpg").toExternalForm())),
    SHALLOW_WATER(true, true, new Image(Cell.class.getResource("/images/landstone.png").toExternalForm())),
    FOUNTAIN(false, true, new Image(Cell.class.getResource("/images/sea.jpg").toExternalForm())),
    SMALL_POND(false, true, new Image(Cell.class.getResource("/images/gulf_tile.jpg").toExternalForm())),
    BIG_POND(false, true, new Image(Cell.class.getResource("/images/gulf_tile.jpg").toExternalForm())),
    BEACH(true, true, new Image(Cell.class.getResource("/images/Plain2.jpg").toExternalForm())),
    SEA(false, true, new Image(Cell.class.getResource("/images/sea_tile.jpg").toExternalForm()));

    private final boolean passable;

    private final boolean isWateryLand;
    private Image image;

    TextureType(boolean passable, boolean isWateryLand, Image image) {
        this.passable = passable;
        this.isWateryLand = isWateryLand;
        this.image = image;
    }

    public boolean isWateryLand() {
        return isWateryLand;
    }

    public boolean isWatery() {
        return this.isWateryLand;
    }

    public boolean isPassable() {
        return passable;
    }

    public Image getImage() {
        return image;
    }
}
