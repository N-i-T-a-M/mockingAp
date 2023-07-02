package model.Building;

import model.Kingdom;

public class Wall extends Building {
    private boolean isStaircase;

    public Wall(BuildingType buildingType, Kingdom owner, int xPosition, int yPosition) {
        super(buildingType, owner, xPosition, yPosition);
    }
}
