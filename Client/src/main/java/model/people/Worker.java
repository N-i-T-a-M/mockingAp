package model.people;

import model.Building.Building;
import model.Kingdom;

public class Worker extends Unit {
    private Building workPlace;

    public Worker(Kingdom homeland, UnitType unitType, int xPosition, int yPosition) {
        super(homeland, unitType, xPosition, yPosition);
        workPlace = null;
    }

    public void setWorkPlace(Building workPlace) {
        this.workPlace = workPlace;
    }

    public Building getWorkPlace() {
        return workPlace;
    }
}
