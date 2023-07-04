package model.people.soldier;

import model.Kingdom;
import model.people.Unit;
import model.people.UnitType;


public class Soldier extends Unit {

    private final Situation situation;


    public Soldier(Kingdom owner, UnitType unitType, int xPosition, int yPosition) {
        super(owner, unitType, xPosition, yPosition);
        situation = Situation.STANDING;

    }


    public Situation getSituation() {
        return situation;
    }

}
