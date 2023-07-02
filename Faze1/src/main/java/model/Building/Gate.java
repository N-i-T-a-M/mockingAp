package model.Building;

import model.Kingdom;

public class Gate extends Building {

    private boolean isOpen;

    public Gate(BuildingType buildingType, Kingdom owner, int xPosition, int yPosition) {
        super(buildingType, owner, xPosition, yPosition);
        isOpen = false;
    }

    public void changeDoorStatus() {
        isOpen = !isOpen;
    }

    public boolean isDoorOpen() {
        return isOpen;
    }
}
