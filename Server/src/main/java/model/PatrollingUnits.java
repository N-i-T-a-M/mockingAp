package model;

import model.map.Cell;
import model.people.Unit;

import java.util.ArrayList;

public class PatrollingUnits {
    private final int xStart;
    private final int xEnd;
    private final int yStart;
    private final int yEnd;
    private final ArrayList<Unit> units;
    private final Game game;
    private final ArrayList<Cell> path;
    private int patrollingUnitsNumberOfRounds;

    public int getxStart() {
        return xStart;
    }

    public int getxEnd() {
        return xEnd;
    }

    public void setPatrollingUnitsNumberOfRounds(int increment) {
        this.patrollingUnitsNumberOfRounds += increment;
    }

    public int getyStart() {
        return yStart;
    }

    public int getyEnd() {
        return yEnd;
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public Game getGame() {
        return game;
    }

    public ArrayList<Cell> getPath() {
        return path;
    }

    public int getPatrollingUnitsNumberOfRounds() {
        return patrollingUnitsNumberOfRounds;
    }

    public PatrollingUnits(int xStart, int xEnd, int yStart, int yEnd, ArrayList<Unit> units, Game game) {
        this.xStart = xStart;
        this.xEnd = xEnd;
        this.yStart = yStart;
        this.yEnd = yEnd;
        this.units = units;
        this.game = game;
        this.path = this.game.finalPath(xStart, yStart, xEnd, yEnd);
        this.patrollingUnitsNumberOfRounds = 0;
    }
}
