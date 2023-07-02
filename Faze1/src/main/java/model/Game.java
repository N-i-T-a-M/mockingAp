package model;

import controller.GameController.GameMenuController;
import model.Building.*;
import model.map.Cell;
import model.map.Map;
import model.people.Unit;
import model.people.UnitType;

import java.util.ArrayList;
import java.util.Random;

public class Game {


    private final ArrayList<Kingdom> kingdoms = new ArrayList<>();
    public ArrayList<Kingdom> getKingdoms() {
        return kingdoms;
    }
    static Game yetGame;


    public Game(Map currentMap, ArrayList<Kingdom> players) {
        this.currentMap = currentMap;
        this.players = players;
        this.currentKingdom = players.get(0);
        this.roundsPassed = 0;
    }

    private final Map currentMap;
    private Building selectedBuilding;
    private final ArrayList<Kingdom> players;

    private Kingdom currentKingdom;
    private final ArrayList<Unit> selectedUnits = new ArrayList<>();
    private final ArrayList<ArrayList<Unit>> movingUnits = new ArrayList<>();

    public ArrayList<ArrayList<Unit>> getMovingUnits() {
        return movingUnits;
    }

    private final ArrayList<PatrollingUnits> patrollingUnits = new ArrayList<>();
    private final ArrayList<ArrayList<Unit>> attackingUnits = new ArrayList<>();

    public ArrayList<ArrayList<Unit>> getAttackingUnits() {
        return attackingUnits;
    }

    public ArrayList<PatrollingUnits> getPatrollingUnits() {
        return patrollingUnits;
    }

    public ArrayList<Unit> getSelectedUnits() {
        return selectedUnits;
    }

    public void setSelectedUnits(Unit unit) {
        this.selectedUnits.add(unit);
    }

    public void clearSelectedUnits() {
        this.selectedUnits.clear();
    }

    private int roundsPassed;

    public void dropBuilding(int x, int y, BuildingType buildingType) {
        currentKingdom.addGold(-buildingType.getGoldPrice());
        currentKingdom.setNumberOfWorkers(currentKingdom.getNumberOfWorkers() + 1);

        Building building = BuildingType.getBuildingByBuildingType(buildingType, currentKingdom, x, y);
        currentMap.getMap()[x][y].setBuilding(building);
        currentMap.getMap()[x][y].setHeight(building.getHeight());
        if (building instanceof ProductionCenter) {
            currentKingdom.addProductionCenter((ProductionCenter) building);
        } else if (building instanceof UnitCreation) {
            currentKingdom.addToAllUnitCreations((UnitCreation) building);
        } else if (buildingType == BuildingType.STOCKPILE) {
            currentKingdom.addToStockPiles((Storage) building);
        } else if (buildingType == BuildingType.FOOD_STOCKPILE) {
            currentKingdom.addToFoodStockPiles((Storage) building);
        } else if (buildingType == BuildingType.ARMOURY) {
            currentKingdom.addToWeapons((Storage) building);
            currentKingdom.addToDefensiveWeapon((Storage) building);
        } else if (buildingType == BuildingType.STABLE) {
            currentKingdom.addToStables((Storage) building);
        }
    }

    public void selectBuilding(int x, int y) {
        this.selectedBuilding = currentMap.getMap()[x][y].getBuilding();
    }

    public Map getCurrentMap() {
        return currentMap;
    }

    public Kingdom getCurrentKingdom() {
        return currentKingdom;
    }

    public boolean isEnemyExistsInCell(int x, int y) {
        for (Unit unit : currentMap.getMap()[x][y].getUnits()) {
            if (unit.getHomeland() != currentKingdom) {
                return true;
            }
        }
        return false;
    }

    public void nextTurn() {
        int currentKingdomNumber = -1;

        for (int i = 0; i < players.size(); i++) {
            if (currentKingdom == players.get(i)) {
                currentKingdomNumber = i;
            }
        }
        for (ProductionCenter productionCenter : currentKingdom.getAllProductionCenters()) {
            productionCenter.run();
        }
        for (UnitCreation unitCreation : currentKingdom.getAllUnitCreations()) {
            unitCreation.run();
        }
        if (currentKingdomNumber == (players.size() - 1)) {
            checkBuildingsOnFire();
            healSickCells();
            checkSickCells();
            makeSickCells();
            roundsPassed++;
            currentKingdom = players.get(0);
        } else {
            currentKingdom = players.get(currentKingdomNumber + 1);
        }
    }


    public String patrolUnit(PatrollingUnits patrollingUnit) {
        int xStart = patrollingUnit.getxStart();
        int yStart = patrollingUnit.getyStart();
        int xEnd = patrollingUnit.getxEnd();
        int yEnd = patrollingUnit.getyEnd();
        int speed = patrollingUnit.getUnits().get(0).getSpeed();
        if (patrollingUnit.getPatrollingUnitsNumberOfRounds() % 2 == 0) {//go towards the start
            ArrayList<Cell> path = finalPath(patrollingUnit.getUnits().get(0).getxPosition(), patrollingUnit.getUnits().get(0).getyPosition(), xStart, yStart);
            if (speed < path.size()) {
                moveUnit(patrollingUnit.getUnits().get(0).getxPosition(), patrollingUnit.getUnits().get(0).getyPosition(), xStart, yStart, patrollingUnit.getUnits());
            } else {
                moveUnit(patrollingUnit.getUnits().get(0).getxPosition(), patrollingUnit.getUnits().get(0).getyPosition(), xStart, yStart, patrollingUnit.getUnits());
                patrollingUnit.setPatrollingUnitsNumberOfRounds(1);
                speed = speed - path.size() + 1;
                moveUnitWithSpeed(xStart, yStart, xEnd, yEnd, speed, patrollingUnit.getUnits());
            }
        } else if (patrollingUnit.getPatrollingUnitsNumberOfRounds() % 2 == 1) {//go towards the end
            ArrayList<Cell> path = finalPath(patrollingUnit.getUnits().get(0).getxPosition(), patrollingUnit.getUnits().get(0).getyPosition(), xEnd, yEnd);
            if (speed < path.size()) {
                moveUnit(patrollingUnit.getUnits().get(0).getxPosition(), patrollingUnit.getUnits().get(0).getyPosition(), xEnd, yEnd, patrollingUnit.getUnits());
            } else {
                moveUnit(patrollingUnit.getUnits().get(0).getxPosition(), patrollingUnit.getUnits().get(0).getyPosition(), xEnd, yEnd, patrollingUnit.getUnits());
                patrollingUnit.setPatrollingUnitsNumberOfRounds(1);
                speed = speed - path.size() + 1;
                moveUnitWithSpeed(xEnd, yEnd, xStart, yStart, speed, patrollingUnit.getUnits());
            }
        }
        return "";
    }

    public void moveUnitWithSpeed(int xStart, int yStart, int xEnd, int yEnd, int speed, ArrayList<Unit> units) {
        ArrayList<Cell> path = finalPath(xStart, yStart, xEnd, yEnd);
        for (int i = 0; i < speed && i < path.size() - 2; i++) {
            for (int j = units.size() - 1; j >= 0; j--) {
                path.get(i).getUnits().remove(units.get(j));
                path.get(i + 1).getUnits().add(units.get(j));
            }
        }
    }

    public String moveUnit(int xStart, int yStart, int xEnd, int yEnd, ArrayList<Unit> units) {
        ArrayList<Cell> path = finalPath(xStart, yStart, xEnd, yEnd);
        if (path == null || path.get(0) == null) {
            return "no path found for these units";
        }
        int speed = units.get(0).getSpeed();
        for (Unit unit : units) {
            path.get((Math.min(speed, path.size() - 1))).addUnits(unit);
            if (unit != null) {
                unit.setxPosition(path.get(Math.min(speed, path.size() - 1)).getxCoordinate());

                unit.setyPosition(path.get(Math.min(speed, path.size() - 1)).getyCoordinate());
            }
        }
        for (int j = units.size() - 1; j >= 0; j--) {
            path.get(0).getUnits().remove(units.get(j));
        }
        return "units moved successfully";
    }

    public ArrayList<Cell> finalPath(int xStart, int yStart, int xEnd, int yEnd) {
        runPath(xStart, yStart, xEnd, yEnd);
        ArrayList<Cell> path = new ArrayList<>();
        Cell cell = currentMap.getMap()[xEnd][yEnd];
        path.add(cell);
        if (cell.getFather() == null) {
            return null;
        }
        while (!path.contains(currentMap.getMap()[xStart][yStart])) {
            if (cell.getFather() == null) {
                return null;
            }
            cell = cell.getFather();
            path.add(cell);
        }
        ArrayList<Cell> correctOrder = new ArrayList<>();
        for (int i = path.size() - 1; i >= 0; i--) {
            correctOrder.add(path.get(i));
        }
        for (int i = 0; i < currentMap.getDimension(); i++) {
            for (int j = 0; j < currentMap.getDimension(); j++) {
                currentMap.getMap()[i][j].setFather(null);
                currentMap.getMap()[i][j].setInThePath(false);
            }
        }
        return correctOrder;
    }

    private void runPath(int xStart, int yStart, int xEnd, int yEnd) {
        ArrayList<Cell> way = new ArrayList<>();
        way.add(currentMap.getMap()[xStart][yStart]);
        currentMap.getMap()[xStart][yStart].setInThePath(true);
        int previousSize = 0;
        int currentSize = 1;
        while (!way.contains(currentMap.getMap()[xEnd][yEnd])) {
            if (previousSize == currentSize) {
                break;
            }
            for (int i = 0; i < currentMap.getDimension(); i++) {
                for (int j = 0; j < currentMap.getDimension(); j++) {
                    if (!currentMap.getMap()[i][j].isInThePath() && currentMap.getMap()[i][j].isPassable()) {
                        for (Cell cell : neighbors(i, j)) {
                            if (cell.isInThePath()) {
                                way.add(currentMap.getMap()[i][j]);
                                currentMap.getMap()[i][j].setInThePath(true);
                                currentMap.getMap()[i][j].setFather(cell);
                            }
                        }
                    }
                    if (currentMap.getMap()[xEnd][yEnd].isInThePath()) {
                        return;
                    }
                }
            }
            previousSize = currentSize;
            currentSize = way.size();
        }
    }

    public ArrayList<Cell> neighbors(int x, int y) {
        ArrayList<Cell> output = new ArrayList<>();
        if (x >= 1 && currentMap.getMap()[x - 1][y] != null) {
            output.add(currentMap.getMap()[x - 1][y]);
        }
        if (x + 1 < currentMap.getDimension() && currentMap.getMap()[x + 1][y] != null) {
            output.add(currentMap.getMap()[x + 1][y]);
        }
        if (y >= 1 && currentMap.getMap()[x][y - 1] != null) {
            output.add(currentMap.getMap()[x][y - 1]);
        }
        if (y + 1 < currentMap.getDimension() && currentMap.getMap()[x][y + 1] != null) {
            output.add(currentMap.getMap()[x][y + 1]);
        }
        return output;
    }

    public Building getSelectedBuilding() {
        return selectedBuilding;
    }

    public void setSelectedBuilding(Building selectedBuilding) {
        this.selectedBuilding = selectedBuilding;
    }

    public ArrayList<Kingdom> getPlayers() {
        return players;
    }

    public String groundAttack(int x, int y, ArrayList<Unit> units) {
        ArrayList<Cell> path = finalPath(units.get(0).getxPosition(), units.get(0).getyPosition(), x, y);
        if (path == null) {
            if (x != units.get(0).getxPosition() || y != units.get(0).getyPosition())
                return "enemy can't be reached";
            else {
                fight(x, y, units);
                return "fight is done";
            }
        }
        if (path.size() - 1 > units.get(0).getSpeed()) {
            return "enemy out of range, please move your units closer";
        }
        moveUnit(units.get(0).getxPosition(), units.get(0).getyPosition(), x, y, units);
        fight(x, y, units);
        return "fight is done";
    }

    private void fight(int x, int y, ArrayList<Unit> units) {
        Cell cell = this.getCurrentMap().getMap()[x][y];
        for (int j = 0; j < units.size(); j++) {
            Unit unit = units.get(j);
            for (int i = 0; i < cell.getUnits().size(); i++) {
                Unit unit1 = cell.getUnits().get(i);
                if (unit1 != null && unit != null && unit1.getHomeland() != null && unit.getHomeland() != null) {
                    if (unit1.getHomeland() != unit.getHomeland()) {
                        oneIsDead:
                        while (true) {
                            if (unit.getHitPoint() > 0) {
                                if (unit1.getHitPoint() > 0) {
                                    if (!unit1.isBeingHit()) {
                                        unitOnUnitFight(unit, unit1);
                                        unit1.setBeingHit(true);
                                        if (!unit1.isHittingSomeOne() && !unit.isBeingHit()) {
                                            unitOnUnitFight(unit1, unit);
                                            unit.setBeingHit(true);
                                        }
                                    }
                                    if (unit1.isBeingHit() && i == cell.getUnits().size() - 1) {
                                        unitOnUnitFight(unit, unit1);
                                        unit1.setBeingHit(true);
                                        if (!unit1.isHittingSomeOne() && !unit.isBeingHit()) {
                                            unitOnUnitFight(unit1, unit);
                                            unit.setBeingHit(true);
                                        }
                                    }
                                } else {
                                    break;
                                }
                            } else {
                                break;
                            }
                        }
                    }
                }
            }
        }
        for (int i = units.size() - 1; i >= 0; i--) {
            if (units.get(i) != null && units.get(i).getHitPoint() <= 0)
                units.remove(i);
        }
        for (int i = cell.getUnits().size() - 1; i >= 0; i--) {
            if (cell.getUnits().get(i).getHitPoint() <= 0)
                cell.getUnits().remove(i);
        }
        for (Unit unit : units) {
            if (unit != null) {
                unit.setBeingHit(false);
                unit.setHittingSomeOne(false);
            }
        }
        for (Unit unit : cell.getUnits()) {
            if (unit != null) {
                unit.setBeingHit(false);
                unit.setHittingSomeOne(false);
            }
        }
    }

    private void unitOnUnitFight(Unit attacker, Unit mast) {
        int salahShoory = attacker.getHomeland().getFearRate() * (5 * attacker.getHomeland().getFearRate() / 100);
        int damage = attacker.getUnitType().getAttackPower() + salahShoory;
        if (!GameMenuController.isBowMan(attacker)) {
            if (mast.getxPosition() == attacker.getxPosition() && mast.getyPosition() == attacker.getyPosition()) {
                mast.decreaseHitPoint(damage);
            }
        } else {
            int range = attacker.getUnitType().getRange();
            int range2 = attacker.getUnitType().getSecondRange();
            Cell cell = this.getCurrentMap().getMap()[attacker.getxPosition()][attacker.getyPosition()];
            if (cell.getBuilding() != null && cell.getBuilding() instanceof Tower) {
                Tower tower = (Tower) cell.getBuilding();
                range += tower.getFireRange();
                range2 += tower.getDefendRange();
            }
            if (Math.pow(mast.getxPosition() - attacker.getxPosition(), 2) + Math.pow(mast.getyPosition() - attacker.getyPosition(), 2) <= range &&
                    Math.pow(mast.getxPosition() - attacker.getxPosition(), 2) + Math.pow(mast.getyPosition() - attacker.getyPosition(), 2) >= range2) {
                mast.decreaseHitPoint(damage);
            }
        }
    }

    public void airAttack(int x, int y, ArrayList<Unit> units) {
        int range = units.get(0).getUnitType().getRange();
        int range2 = units.get(0).getUnitType().getSecondRange();
        Cell cell = this.getCurrentMap().getMap()[units.get(0).getxPosition()][units.get(0).getyPosition()];
        if (cell.getBuilding() != null && cell.getBuilding() instanceof Tower) {
            Tower tower = (Tower) cell.getBuilding();
            range += tower.getFireRange();
            range2 += tower.getDefendRange();
        }
        if (Math.pow(x - units.get(0).getxPosition(), 2) + Math.pow(y - units.get(0).getyPosition(), 2) <= range
                && Math.pow(x - units.get(0).getxPosition(), 2) + Math.pow(y - units.get(0).getyPosition(), 2) >= range2) {
            fight(x, y, units);
        }
    }

    public void setCurrentKingdom(Kingdom currentKingdom) {
        this.currentKingdom = currentKingdom;
    }



    public String showBuildings() {
        String output = "";
        for (int i = 0; i < currentMap.getDimension(); i++) {
            for (int j = 0; j < currentMap.getDimension(); j++) {
                Cell cell = currentMap.getMap()[i][j];
                if (cell.getBuilding() != null) {
                    if (cell.getBuilding().getOwner() == currentKingdom) {
                        output += cell.getBuilding().toString();
                    }
                }
            }
        }
        return output;
    }

    public String showPeople() {
        String output = "";
        for (int i = 0; i < currentMap.getDimension(); i++) {
            for (int j = 0; j < currentMap.getDimension(); j++) {
                Cell cell = currentMap.getMap()[i][j];
                for (Unit unit : cell.getUnits()) {
                    if (unit != null && unit.getHomeland() != null && unit.getHomeland() == currentKingdom) {
                        output += unit + "\n";
                    }
                }
            }
        }
        return output;
    }

    public static Game getYetGame() {
        return yetGame;
    }
    private void makeSickCells(){
        for (Cell[] cells : currentMap.getMap()) {
            for (Cell cell: cells) {
                if(getCellOwner(cell) != null) {
                    Random random = new Random();
                    int sickProbably = random.nextInt(11);
                    if (sickProbably == 1) {
                        cell.setSick(true);
                    }
                }
            }
        }
    }
    private void checkSickCells(){
        for (Cell[] cells : currentMap.getMap()) {
            for (Cell cell: cells) {
                if (cell.isSick() && getCellOwner(cell) != null){
//                    System.out.println(cell.getxCoordinate() + " " + cell.getyCoordinate());
                    getCellOwner(cell).addPopularity(-5);
                }
            }
        }
    }

    public Kingdom getCellOwner(Cell cell){
        for (Kingdom kingdom : players) {
            int xPath = Math.abs(kingdom.getHeadSquare().getxCoordinate() - cell.getxCoordinate());
            int yPath = Math.abs(kingdom.getHeadSquare().getyCoordinate() - cell.getyCoordinate());
            if((xPath + yPath) < 40){
                return kingdom;
            }
        }
        return null;
    }

    private void healSickCells(){
        for (Cell[] cells : currentMap.getMap()) {
            for (Cell cell: cells) {
                if (cell.isSick() && getCellOwner(cell) != null){
                    for (Unit unit : cell.getUnits()) {
                        if(unit.getUnitType() == UnitType.DOCTOR && unit.getHomeland() == getCellOwner(cell)){
                            cell.setSick(false);
                        }
                    }
                }
            }
        }
    }
    private void checkBuildingsOnFire(){
        for (Cell[] cells : currentMap.getMap()) {
            for (Cell cell: cells) {
                if(cell.getBuilding() != null && cell.getBuilding().isOnFire()){
                    cell.getBuilding().setHitPoint(cell.getBuilding().getHitPoint() - 5);
                    cell.getBuilding().setInFireTurn(cell.getBuilding().getInFireTurn()-1);
                    if(cell.getBuilding().getInFireTurn() == 0){
                        cell.getBuilding().setOnFire(false);
                    }
                }
            }
        }
    }
}

