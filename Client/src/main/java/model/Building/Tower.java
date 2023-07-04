package model.Building;

import Enums.BuildingType;
import model.Kingdom;

public class Tower extends Building {

    private int fireRange;
    private int defendRange;


    public int getFireRange() {
        return fireRange;
    }

    public int getDefendRange() {
        return defendRange;
    }

    public Tower(BuildingType buildingType, Kingdom owner, int xPosition, int yPosition) {
        super(buildingType, owner, xPosition, yPosition);
        switch (buildingType) {
            case CIRCLE_TOWER:
            case SQUARE_TOWER:
                fireRange = 3;
                defendRange = 1;
                break;
            case PERIMETER_TOWER:
                fireRange = 4;
                defendRange = 2;
                break;
            case DEFENCE_TURRET:
                fireRange = 2;
                defendRange = 1;
                break;
            case LOOKOUT_TOWER:
                fireRange = 5;
                defendRange = 2;
                break;
//            case MAIN_CASTLE:
//                fireRange = 0;
//               break;
        }
    }
}
