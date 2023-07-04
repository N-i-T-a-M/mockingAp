package controller.gamecontroller;

import model.Building.Building;
import Enums.BuildingType;
import model.Equipment.Equipment;
import model.Equipment.EquipmentType;
import model.*;
import model.Property.Resources;
import model.map.Cell;
import model.people.Unit;
import model.people.UnitType;

import java.util.ArrayList;
import java.util.Objects;

public class GameMenuController {
    public GameMenuController(Game newGame) {
        this.newGame = newGame;
        this.buildingController = new BuildingController(newGame);
        this.kingdomController = new KingdomController(newGame.getCurrentKingdom());
    }

    private Game newGame;

    public void setCurrentKingdom(User user) {
        for (Kingdom kingdom : newGame.getPlayers()) {
            if (kingdom.getOwner().equals(user)) {
                newGame.setCurrentKingdom(kingdom);
                return;
            }
        }
    }

    public void setNewGame(Game newGame) {
        this.newGame = newGame;
    }

    private final KingdomController kingdomController;
    private final BuildingController buildingController;

    public Game getNewGame() {
        return newGame;
    }

    public String dropBuilding(String X, String Y, String type) {
        String output = checkNumber(X);
        if (!output.equals("")) {
            return output;
        }
        output = checkNumber(Y);
        if (!output.equals("")) {
            return output;
        }
        int x = Integer.parseInt(X);
        int y = Integer.parseInt(Y);
        if (type.isEmpty()) {
            return "type can't be empty";
        }
        BuildingType buildingType = BuildingType.getBuildingTypeByName(type);
        if (buildingType == null) {
            return "building name is not correct";
        }
        if (x < 0 || y < 0 || x >= newGame.getCurrentMap().getDimension() || y >= newGame.getCurrentMap().getDimension()) {
            return "your coordinates are not correct";
        }
        if (newGame.getCurrentMap().getMap()[x][y].getBuilding() != null) {
            return "there is a building here!";
        }
        if (!BuildingType.isBuildingMatchTexture(buildingType, newGame.getCurrentMap().getMap()[x][y].getTextureType())) {
            return "invalid texture!";
        }
        if (newGame.isEnemyExistsInCell(x, y)) {
            return "there is already an enemy in this cell and you cannot drop it!";
        }
        if (newGame.getCurrentKingdom().getGold() < buildingType.getGoldPrice()) {
            return "you don't have enough gold to build this building!";
        }
        if (buildingType.getResources() != null) {
            Resources resources = new Resources(buildingType.getResourcePrice(), buildingType.getResourceCount());
            if (buildingType.getResourceCount() > newGame.getCurrentKingdom().getNumberOfProperties(resources)) {
                return "you don't have enough " + buildingType.getResourcePrice().getName() + " to build this building!";
            }
            newGame.getCurrentKingdom().spendProperties(resources);
        }
        int check = 0;
        if (buildingType == BuildingType.STOCKPILE) {
            ArrayList<Cell> neighbors = newGame.neighbors(x, y);
            for (Cell cell : neighbors) {
                if (cell.getBuilding() != null) {
                    if (cell.getBuilding().getBuildingType() == buildingType && cell.getBuilding().getOwner().equals(newGame.getCurrentKingdom())) {
                        check = 1;
                        break;
                    }
                }
            }
            if (check == 0) {
                return "you should build stockpile near your other stockpiles!";
            }
        }
        newGame.dropBuilding(x, y, buildingType);
        return "make building successfully!";
    }

    public String selectBuilding(String X, String Y) {
        String output = checkNumber(X);
        if (!output.equals("")) {
            return output;
        }
        output = checkNumber(Y);
        if (!output.equals("")) {
            return output;
        }
        int x = Integer.parseInt(X);
        int y = Integer.parseInt(Y);
        if (x < 0 || y < 0 || x >= newGame.getCurrentMap().getDimension() || y >= newGame.getCurrentMap().getDimension()) {
            return "your coordinates are not correct";
        }
        if (newGame.getCurrentMap().getMap()[x][y].getBuilding() == null) {
            return "there is not any building here!";
        }
        if (newGame.getCurrentMap().getMap()[x][y].getBuilding().getOwner() != newGame.getCurrentKingdom()) {
            return "this building is not for you!";
        }
        newGame.selectBuilding(x, y);
        BuildingController.building = newGame.getSelectedBuilding();
        return "building selected successfully!";
    }

    public String createUnit(String type, String count) {
        if (type.isEmpty()) {
            return "Type can't be empty";
        }
        if (count.isEmpty()) {
            return "Count can't be empty";
        }
        int number;
        try {
            number = Integer.parseInt(count);
        } catch (NumberFormatException e) {
            return "Count must be a number";
        }
        if (number <= 0) {
            return "Count must be positive";
        }
        return buildingController.createUnit(type, number);
    }

    public String dropUnit(String X, String Y, String type, String count) {
        String output = checkNumber(X);
        if (!output.equals("")) {
            return output;
        }
        output = checkNumber(Y);
        if (!output.equals("")) {
            return output;
        }
        int x = Integer.parseInt(X);
        int y = Integer.parseInt(Y);
        if (type.isEmpty()) {
            return "Type can't be empty";
        }
        if (count.isEmpty()) {
            return "Count can't be empty";
        }
        int number;
        try {
            number = Integer.parseInt(count);
        } catch (NumberFormatException e) {
            return "Count must be a number";
        }
        if (number <= 0) {
            return "Count must be positive";
        }
        if (x < 0 || y < 0 || x >= newGame.getCurrentMap().getDimension() || y >= newGame.getCurrentMap().getDimension()) {
            return "your coordinates are not correct";
        }
        if (UnitType.getUnitTypeByName(type) == null) {
            return "unit name is not correct";
        }
        if (!newGame.getCurrentMap().getMap()[x][y].getTextureType().isPassable()) {
            return "units can't be here!";
        }
        if (newGame.getCurrentMap().getMap()[x][y].getBuilding() != null) {
            if (newGame.getCurrentKingdom().getHeadSquare().getxCoordinate() != x || newGame.getCurrentKingdom().getHeadSquare().getyCoordinate() != y) {
                return "units can't be on buildings";
            }
        }
        for (int i = 0; i < number; i++) {
            Unit unit = new Unit(newGame.getCurrentKingdom(), Objects.requireNonNull(UnitType.getUnitTypeByName(type)), x, y);
            unit.setxPosition(x);
            unit.setyPosition(y);
            newGame.getCurrentMap().getMap()[x][y].addUnits(unit);
            newGame.getCurrentKingdom().getUnits().add(unit);
        }
        return "units dropped successfully!";
    }

    public String setFoodRate(String number) throws NumberFormatException {
        return kingdomController.setFoodRate(number);
    }

    public String setTaxRate(String number) throws NumberFormatException {
        return kingdomController.setTaxRate(number);
    }

    public String showTaxRate() {
        return kingdomController.showTaxRate();
    }

    public String setFearRate(String number) throws NumberFormatException {
        return kingdomController.setFearRate(number);
    }

    public String repair() {
        return buildingController.repair();
    }

    public String showFoodRate() {
        return kingdomController.showFoodRate();
    }

    public String showFoodList() {
        return kingdomController.showFoodList();
    }

    public String showPopularity() {
        return kingdomController.showPopularity();
    }

    public String showPopularityFactors() {
        return kingdomController.showPopularityFactors();
    }

    public String selectUnit(String xCoordinate, String yCoordinate, String type) {
        String output = checkNumber(xCoordinate);
        if (!output.equals("")) {
            return output;
        }
        output = checkNumber(yCoordinate);
        if (!output.equals("")) {
            return output;
        }
        if (type.isEmpty()) {
            return "type can't be empty";
        }
        int x = Integer.parseInt(xCoordinate);
        int y = Integer.parseInt(yCoordinate);
        if (x < 0 || y < 0 || x >= newGame.getCurrentMap().getDimension() || y >= newGame.getCurrentMap().getDimension()) {
            return "your coordinates are not correct";
        }
        if (UnitType.getUnitTypeByName(type) == null) {
            return "your unit type is incorrect!";
        }
        newGame.getSelectedUnits().clear();
        int selected = 0, yours = 0;
        if (newGame.getCurrentMap().getMap()[x][y].getUnits() == null) {
            return "there's no unit here!";
        }
        for (Unit unit : newGame.getCurrentMap().getMap()[x][y].getUnits()) {
            if (unit.getHomeland() == newGame.getCurrentKingdom()) {
                if (unit.getUnitType() == UnitType.getUnitTypeByName(type)) {
                    newGame.getSelectedUnits().add(unit);
                    selected++;
                }
                yours++;
            }
        }
        if (yours == 0) {
            return "None of your units are here";
        }
        if (selected == 0) {
            return "There's no unit of this type here";
        }
        return "units selected successfully!";
    }

    public String moveUnit(String xCoordinate, String yCoordinate) {
        String output = checkNumber(xCoordinate);
        if (!output.equals("")) {
            return output;
        }
        output = checkNumber(yCoordinate);
        if (!output.equals("")) {
            return output;
        }
        int x = Integer.parseInt(xCoordinate);
        int y = Integer.parseInt(yCoordinate);
        if (x < 0 || y < 0 || x > newGame.getCurrentMap().getDimension() || y > newGame.getCurrentMap().getDimension()) {
            return "your coordinates are not correct";
        }
        if (!newGame.getCurrentMap().getMap()[x][y].isPassable()) {
            return "the destination is not a valid destination, troops can't be there";
        }
        if (newGame.getSelectedUnits().size() == 0 || newGame.getSelectedUnits().get(0) == null) {
            return "there's no unit selected";
        }
        if (newGame.getSelectedUnits().get(0).getSpeed() == 0) {
            return "this unit can't move";
        }
        int xStart = newGame.getSelectedUnits().get(0).getxPosition();
        int yStart = newGame.getSelectedUnits().get(0).getyPosition();
        int xEnd = x;
        int yEnd = y;
        if (xStart == xEnd && yStart == yEnd) {
            return "you are already here";
        }
        ArrayList<Cell> path = newGame.finalPath(xStart, yStart, xEnd, yEnd);
        if (path == null) {
            return "no path found for these units";
        }
        if (newGame.getMovingUnits().size() != 0) {
            for (ArrayList<Unit> units : newGame.getMovingUnits()) {
                if (units.get(0).getxPosition() == newGame.getSelectedUnits().get(0).getxPosition()
                        && units.get(0).getyPosition() == newGame.getSelectedUnits().get(0).getyPosition()) {
                    newGame.getMovingUnits().remove(units);
                    break;
                }
            }
        }
        setDestination(xEnd, yEnd);
        newGame.getMovingUnits().add(newGame.getSelectedUnits());
        resetSelectedUnits();
        return "units set to move";
    }

    public String patrolUnit(String x1Coordinate, String y1Coordinate, String x2Coordinate, String y2Coordinate) {
        String output = checkNumber(x1Coordinate);
        if (!output.equals("")) {
            return output;
        }
        output = checkNumber(y1Coordinate);
        if (!output.equals("")) {
            return output;
        }
        output = checkNumber(x2Coordinate);
        if (!output.equals("")) {
            return output;
        }
        output = checkNumber(y2Coordinate);
        if (!output.equals("")) {
            return output;
        }
        int x1 = Integer.parseInt(x1Coordinate);
        int y1 = Integer.parseInt(y1Coordinate);
        int x2 = Integer.parseInt(x2Coordinate);
        int y2 = Integer.parseInt(y2Coordinate);
        if (x1 < 0 || y1 < 0 || x2 < 0 || y2 < 0 || x1 >= newGame.getCurrentMap().getDimension() || y1 >= newGame.getCurrentMap().getDimension() || x2 >= newGame.getCurrentMap().getDimension() || y2 >= newGame.getCurrentMap().getDimension()) {
            return "your coordinates are not correct";
        }
        if (x1 == x2 && y1 == y2) {
            return "you can't patrol a unit in one place";
        }
        if (!isUnitSelected().equals("true")) {
            return isUnitSelected();
        }
        if (newGame.getSelectedUnits().get(0).getSpeed() == 0) {
            return "this unit can't move";
        }
        if (!newGame.getCurrentMap().getMap()[x1][y1].isPassable()) {
            return "the end point is not a valid destination, troops can't be there";
        }
        if (!newGame.getCurrentMap().getMap()[x2][y2].isPassable()) {
            return "the start point is not a valid destination, troops can't be there";
        }
        int xStart = newGame.getSelectedUnits().get(0).getxPosition();
        int yStart = newGame.getSelectedUnits().get(0).getyPosition();
        ArrayList<Cell> path1 = newGame.finalPath(xStart, yStart, x1, y1);
        ArrayList<Cell> path2 = newGame.finalPath(xStart, yStart, x2, y2);
        if (path1 == null && path2 == null) {
            return "no path found to the patrolling points";
        }
        if (path1.size() - 1 > newGame.getSelectedUnits().get(0).getSpeed() && path2.size() - 1 > newGame.getSelectedUnits().get(0).getSpeed()) {
            return "the points are too far to patrol";
        }
        ArrayList<Cell> path = newGame.finalPath(x1, y1, x2, y2);
        if (path == null) {
            return "there's no path between patrolling points";
        }
        PatrollingUnits patrollingUnits = new PatrollingUnits(x1, y1, x2, y2, newGame.getSelectedUnits(), newGame);
        for (PatrollingUnits units : newGame.getPatrollingUnits()) {
            if (units.getxStart() == patrollingUnits.getxStart() && units.getyStart() == patrollingUnits.getyStart() && units.getxEnd() == patrollingUnits.getxEnd() && units.getyEnd() == patrollingUnits.getyEnd()) {
                newGame.getPatrollingUnits().remove(units);
            }
        }
        newGame.getPatrollingUnits().add(patrollingUnits);
        resetSelectedUnits();
        return "units sent to patrol successfully";
    }

    public String setMode(String xCoordinate, String yCoordinate, String mode, String type) {
        String output = checkNumber(xCoordinate);
        if (!output.equals("")) {
            return output;
        }
        output = checkNumber(yCoordinate);
        if (!output.equals("")) {
            return output;
        }
        int x = Integer.parseInt(xCoordinate);
        int y = Integer.parseInt(yCoordinate);
        if (x < 0 || y < 0 || x >= newGame.getCurrentMap().getDimension() || y >= newGame.getCurrentMap().getDimension()) {
            return "your coordinates are not correct";
        }
        if (mode.isEmpty()) {
            return "mode can't be empty";
        }
        if (!mode.equals("offensive") && !mode.equals("defensive") && !mode.equals("standing")) {
            return "mode is not correct";
        }
        if (type.isEmpty()) {
            return "type can't be empty";
        }
        if (newGame.getCurrentMap().getMap()[x][y].getUnits() == null) {
            return "there's no unit here!";
        }
        int thisTypeUnit = 0;
        for (Unit unit : newGame.getCurrentMap().getMap()[x][y].getUnits()) {
            if (unit.getUnitType() == UnitType.getUnitTypeByName(type)) {
                unit.setMode(mode);
                thisTypeUnit++;
            }
        }
        if (thisTypeUnit == 0) {
            return "There's no unit of this type here";
        }
        return "mode set successfully!";
    }

    public String disbandUnit() {
        if (newGame.getSelectedUnits().size() == 0 || newGame.getSelectedUnits() == null) {
            return "no unit to disband";
        }
        newGame.moveUnitWithSpeed(newGame.getSelectedUnits().get(0).getxPosition(), newGame.getSelectedUnits().get(0).getyPosition(),
                newGame.getCurrentKingdom().getHeadSquare().getxCoordinate(), newGame.getCurrentKingdom().getHeadSquare().getyCoordinate(),
                1000, newGame.getSelectedUnits());
        newGame.getSelectedUnits().clear();
        return "units disbanded successfully!";
    }

    public String buildEquipment(String equipmentName) {
        if (equipmentName.isEmpty()) {
            return "equipment name can't be empty";
        }
        for (Unit unit : newGame.getSelectedUnits()) {
            if (unit.getUnitType() != UnitType.ENGINEER) {
                return "this type of units can't build equipment!";
            }
        }
        EquipmentType equipmentType = EquipmentType.getEquipmentTypeByName(equipmentName);
        if (equipmentType == null) {
            return "equipment name is invalid!";
        }
        ArrayList<Unit> availableEngineers = new ArrayList<>();
        for (Unit unit : newGame.getSelectedUnits()) {
            if (!unit.isBusy()) availableEngineers.add(unit);
            if (availableEngineers.size() == equipmentType.getEngineerCount()) break;
        }
        if (availableEngineers.size() < equipmentType.getEngineerCount()) {
            return "you don't have enough engineers for build " + equipmentName;
        }
        if (equipmentType.getCost() > newGame.getSelectedUnits().get(0).getHomeland().getGold()) {
            return "you don't have enough gold";
        }
        for (Unit e : availableEngineers) e.setBusy(true);

//        Equipment equipment = new Equipment(equipmentType,newGame.getSelectedUnits().get(0).getxPosition(), newGame.getSelectedUnits().get(0).getyPosition(), newGame.getCurrentKingdom());
//        newGame.getCurrentKingdom().addEquipment(equipment);
        newGame.getCurrentKingdom().addGold(-equipmentType.getCost());
        Building siegeTent = new Building(BuildingType.SIEGE_TENT, newGame.getCurrentKingdom(), newGame.getSelectedUnits().get(0).getxPosition(), newGame.getSelectedUnits().get(0).getyPosition());
        newGame.getCurrentKingdom().addToSiegeBuildings(siegeTent);
        siegeTent.setEquipmentType(equipmentType);
        siegeTent.setDelay(equipmentType.getDelay());
        newGame.getCurrentMap().getMap()[newGame.getSelectedUnits().get(0).getxPosition()][newGame.getSelectedUnits().get(0).getyPosition()].setBuilding(siegeTent);
        newGame.getCurrentMap().getMap()[newGame.getSelectedUnits().get(0).getxPosition()][newGame.getSelectedUnits().get(0).getyPosition()].setHeight(siegeTent.getHeight());
        return "equipment " + equipmentName + "build successfully!";
    }

    public String digTunnel(String xCoordinate, String yCoordinate) {
        String output = checkNumber(xCoordinate);
        if (!output.equals("")) {
            return output;
        }
        output = checkNumber(yCoordinate);
        if (!output.equals("")) {
            return output;
        }
        int x = Integer.parseInt(xCoordinate);
        int y = Integer.parseInt(yCoordinate);
        if (x < 0 || y < 0 || x >= newGame.getCurrentMap().getDimension() || y >= newGame.getCurrentMap().getDimension()) {
            return "your coordinates are not correct";
        }
        return "this feature is not available yet";
    }

    public String pourOil(String direction) {
        return "this feature is not available yet";
    }

    public String airAttack(String xCoordinate, String yCoordinate) {
        String output = checkNumber(xCoordinate);
        if (!output.equals("")) {
            return output;
        }
        output = checkNumber(yCoordinate);
        if (!output.equals("")) {
            return output;
        }
        int x = Integer.parseInt(xCoordinate);
        int y = Integer.parseInt(yCoordinate);
        if (x < 0 || y < 0 || x >= newGame.getCurrentMap().getDimension() || y >= newGame.getCurrentMap().getDimension()) {
            return "your coordinates are not correct";
        }
        if (!isBowMan(newGame.getSelectedUnits().get(0))) {
            return "please enter a different command for non bowmen troops";
        }
        if (!newGame.isEnemyExistsInCell(x, y)) {
            return "there's no enemy here";
        }
        if (Math.pow(x - newGame.getSelectedUnits().get(0).getxPosition(), 2) + Math.pow(y - newGame.getSelectedUnits().get(0).getyPosition(), 2) > newGame.getSelectedUnits().get(0).getUnitType().getRange()
                || Math.pow(x - newGame.getSelectedUnits().get(0).getxPosition(), 2) + Math.pow(y - newGame.getSelectedUnits().get(0).getyPosition(), 2) < newGame.getSelectedUnits().get(0).getUnitType().getSecondRange()) {
            return "your target is out of range";
        }
        if (newGame.getAttackingUnits().size() != 0) {
            for (ArrayList<Unit> units : newGame.getAttackingUnits()) {
                if (units.get(0).getxPosition() == newGame.getSelectedUnits().get(0).getxPosition() && units.get(0).getyPosition() == newGame.getSelectedUnits().get(0).getyPosition()) {
                    newGame.getAttackingUnits().remove(units);
                    break;
                }
            }
        }
        setDestination(x, y);
        newGame.getAttackingUnits().add(newGame.getSelectedUnits());
        resetSelectedUnits();
        return "fight is being done successfully";
    }

    public static boolean isBowMan(Unit unit) {
        return unit.getUnitType().getSecondRange() != 0;
    }

    public String groundAttack(String xCoordinate, String yCoordinate) {
        String output = checkNumber(xCoordinate);
        if (!output.equals("")) {
            return output;
        }
        output = checkNumber(yCoordinate);
        if (!output.equals("")) {
            return output;
        }
        int x = Integer.parseInt(xCoordinate);
        int y = Integer.parseInt(yCoordinate);
        if (x < 0 || y < 0 || x >= newGame.getCurrentMap().getDimension() || y >= newGame.getCurrentMap().getDimension()) {
            return "your coordinates are not correct";
        }
        if (!isUnitSelected().equals("true")) {
            return isUnitSelected();
        }
        if (isBowMan(newGame.getSelectedUnits().get(0))) {
            return "please enter a different command for bowmen";
        }
        if (!newGame.isEnemyExistsInCell(x, y)) {
            return "there's no enemy here";
        }
        ArrayList<Cell> path = newGame.finalPath(newGame.getSelectedUnits().get(0).getxPosition(), newGame.getSelectedUnits().get(0).getyPosition(), x, y);
        if (path == null) {
            if (x != newGame.getSelectedUnits().get(0).getxPosition() || y != newGame.getSelectedUnits().get(0).getyPosition())
                return "enemy can't be reached";
        }
        if (path != null && path.size() - 1 > newGame.getSelectedUnits().get(0).getSpeed()) {
            return "enemy out of range, please move your units closer";
        }
        if (newGame.getAttackingUnits().size() != 0) {
            for (ArrayList<Unit> units : newGame.getAttackingUnits()) {
                if (units.get(0).getxPosition() == newGame.getSelectedUnits().get(0).getxPosition() && units.get(0).getyPosition() == newGame.getSelectedUnits().get(0).getyPosition()) {
                    newGame.getAttackingUnits().remove(units);
                    break;
                }
            }
        }
        setDestination(x, y);
        newGame.getAttackingUnits().add(newGame.getSelectedUnits());
        resetSelectedUnits();
        return "attack is being done successfully";
    }

    public static String checkNumber(String X) {
        if (X.isEmpty()) {
            return "input can't be empty";
        }
        try {
            int x = Integer.parseInt(X);
        } catch (NumberFormatException e) {
            return "input must be a number";
        }
        return "";
    }

    private String isUnitSelected() {
        if (newGame.getSelectedUnits() == null || newGame.getSelectedUnits().size() == 0) {
            return "you have to select a unit first";
        }
        return "true";
    }

    public String stopPatrolling(String X1, String Y1, String X2, String Y2) {
        String output = checkNumber(X1);
        if (!output.equals("")) {
            return output;
        }
        output = checkNumber(Y1);
        if (!output.equals("")) {
            return output;
        }
        int x1 = Integer.parseInt(X1);
        int y1 = Integer.parseInt(Y1);
        output = checkNumber(X2);
        if (!output.equals("")) {
            return output;
        }
        output = checkNumber(Y2);
        if (!output.equals("")) {
            return output;
        }
        int x2 = Integer.parseInt(X2);
        int y2 = Integer.parseInt(Y2);
        if (x1 < 0 || y1 < 0 || x1 >= newGame.getCurrentMap().getDimension() || y1 >= newGame.getCurrentMap().getDimension()
                || x2 < 0 || y2 < 0 || x2 >= newGame.getCurrentMap().getDimension() || y2 >= newGame.getCurrentMap().getDimension()) {
            return "your coordinates are not correct";
        }
        if (newGame.getPatrollingUnits() == null || newGame.getPatrollingUnits().size() == 0) {
            return "there's no unit patrolling";
        }
        for (PatrollingUnits units : newGame.getPatrollingUnits()) {
            if (units.getxStart() == x1 && units.getyStart() == y1 && units.getxEnd() == x2 && units.getyEnd() == y2) {
                newGame.getPatrollingUnits().remove(units);
                return "patrolling stopped";
            }
        }
        return "No units patrolling with the given coordinates";
    }


    public void checkEquipment(Kingdom kingdom) {
        for (int i = kingdom.getSiegeBuildings().size() - 1; i >= 0; i++) {
            Building tent = kingdom.getSiegeBuildings().get(i);
            if (tent.getDelay() == 0) {
                EquipmentType equipmentType = tent.getEquipmentType();
                Cell cell = newGame.getCurrentMap().getMap()[tent.getxPosition()][tent.getyPosition()];
                cell.setBuilding(null);
                int totalNumberOfEngineers = equipmentType.getEngineerCount();
                for (Unit unit : cell.getUnits()) {
                    if (totalNumberOfEngineers == 0) break;
                    if (unit.getUnitType() == UnitType.ENGINEER && unit.isBusy()) {
                        unit.setBusy(false);
                        totalNumberOfEngineers--;
                    }
                }
                Equipment equipment = new Equipment(equipmentType, tent.getxPosition(), tent.getyPosition(), kingdom);
                kingdom.removeFromSiegeBuildings(tent);
                cell.addUnits(equipment);
            } else tent.subtractDelay(1);
        }
    }

    public String showBuildings() {
        return newGame.showBuildings();
    }

    public String showPeople() {
        return newGame.showPeople();
    }

    public void endTurnMoves() {
        if (newGame.getMovingUnits() == null || newGame.getMovingUnits().size() == 0) return;
        if (newGame.getMovingUnits().get(0) == null) return;
        for (ArrayList<Unit> units : newGame.getMovingUnits()) {
            if (units == null || units.size() == 0) {

            } else
                newGame.moveUnit(units.get(0).getxPosition(), units.get(0).getyPosition(), units.get(0).getDestinationX(), units.get(0).getDestinationY(), units);
        }
    }

    private void setDestination(int xEnd, int yEnd) {
        if (newGame.getSelectedUnits() == null) return;
        for (Unit unit : newGame.getSelectedUnits()) {
            if (unit == null) continue;
            unit.setDestinationX(xEnd);
            unit.setDestinationY(yEnd);
        }
    }

    public void endTurnFights() {
        if (newGame.getAttackingUnits() == null) return;
        for (ArrayList<Unit> units : newGame.getAttackingUnits()) {
            if (units.get(0).getUnitType().getRange() == 0) {
                newGame.groundAttack(units.get(0).getDestinationX(), units.get(0).getDestinationY(), units);
            } else {
                newGame.airAttack(units.get(0).getDestinationX(), units.get(0).getDestinationY(), units);
            }
        }
    }

    public void endOfTurnPatrolling() {
        if (newGame.getPatrollingUnits() == null) return;
        for (PatrollingUnits units : newGame.getPatrollingUnits()) {
            newGame.patrolUnit(units);
        }
    }

    public void resetSelectedUnits() {
        newGame.setSelectedUnits(null);
    }

    public boolean isDefeated(Kingdom kingdom) {
        return kingdom.getKing().getHitPoint() == 0;
    }

    public Kingdom getCurrentKingdom() {
        return newGame.getCurrentKingdom();
    }

    public int getNumberOfRemainingPlayers() {
        int n = 0;
        for (Kingdom kingdom : newGame.getPlayers()) {
            if (!isDefeated(kingdom)) {
                n++;
            }
        }
        return n;
    }

    public User getWinner() {
        for (Kingdom kingdom : newGame.getPlayers()) {
            if (!isDefeated(kingdom)) {
                return kingdom.getOwner();
            }
        }
        return null;
    }

    public void nextTurn() {
        newGame.nextTurn();
        endTurnMoves();
        endTurnFights();
        endOfTurnPatrolling();
        resetSelectedUnits();
    }
}
