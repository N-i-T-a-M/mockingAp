package model.Building;

import model.Kingdom;
import model.people.UnitType;
import model.people.soldier.Soldier;

import java.util.HashMap;

public class UnitCreation extends Building {
    private int rate;
    private final HashMap<UnitType, Integer> allUnit = new HashMap<>() {

    };

    public UnitCreation(BuildingType buildingType, Kingdom owner, int xPosition, int yPosition) {
        super(buildingType, owner, xPosition, yPosition);

        switch (buildingType) {
            case BARRACK:
                for (UnitType type : UnitType.values()) {
                    if (!type.isArab()) {
                        allUnit.put(type, 0);
                    }
                }
                break;
            case MERCENARY_POST:
                for (UnitType type : UnitType.values()) {
                    if (type.isArab()) {
                        allUnit.put(type, 0);
                    }
                }
                break;
            case STABLE:
                allUnit.put(UnitType.HORSE, 0);
        }
    }


    public HashMap<UnitType, Integer> getAllUnit() {
        return allUnit;
    }

    public void run() {
        switch (getBuildingType()) {
            case HOVEL:
                getOwner().addPopulation(8);
                getOwner().addUnEmployed(8);
            case CHURCH:
                getOwner().addPopularity(2);
                Soldier soldier = new Soldier(getOwner(), UnitType.BLACK_MONK, getxPosition(), getyPosition());
                getOwner().addUnit(soldier);
                getOwner().addSoldier(soldier);
        }
    }

}
