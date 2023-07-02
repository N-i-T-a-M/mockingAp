package model.map;


import java.util.ArrayList;

public class Map {

    private final int dimension;
    private final Cell[][] map;

    private final ArrayList<Cell> headSquares;

    private int lastX;
    private int lastY;

    public Map(int dimension, int kingdomNumber) {
        map = new Cell[dimension][dimension];
        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension; j++)
                map[i][j] = new Cell(i, j);
        this.dimension = dimension;
        headSquares = new ArrayList<>();
        for (int i = 0; i < kingdomNumber; i++) {
            switch (i) {
                case 0:
                    headSquares.add(map[1][1]);
                    break;
                case 1:
                    headSquares.add(map[dimension - 2][dimension - 2]);
                    break;
                case 2:
                    headSquares.add(map[1][dimension - 2]);
                    break;
                case 3:
                    headSquares.add(map[dimension - 2][1]);
                    break;
                case 4:
                    headSquares.add(map[1][dimension / 2]);
                    break;
                case 5:
                    headSquares.add(map[dimension / 2][1]);
                    break;
                case 6:
                    headSquares.add(map[dimension - 2][dimension / 2]);
                    break;
                case 7:
                    headSquares.add(map[dimension / 2][dimension - 2]);
                    break;
            }
        }
//        map[10][10].addUnits(new Unit(new Kingdom(UserDatabase.getCurrentUser()), UnitType.ARABIAN_SWORDSMAN, 9, 9));
    }

    public Cell[][] getMap() {
        return map;
    }

    public int getDimension() {
        return dimension;
    }

    public int getLastX() {
        return lastX;
    }

    public void setLastX(int lastX) {
        this.lastX = lastX;
    }

    public int getLastY() {
        return lastY;
    }

    public ArrayList<Cell> getHeadSquares() {
        return headSquares;
    }

    public void setLastY(int lastY) {
        this.lastY = lastY;
    }
}