package model.people;

import model.Kingdom;

public class Unit {
    protected Kingdom homeland;
    protected boolean isOily;
    protected int xPosition;

    protected int yPosition;
    protected int destinationX;
    protected int destinationY;

    public int getDestinationX() {
        return destinationX;
    }

    public void setDestinationX(int destinationX) {
        this.destinationX = destinationX;
    }

    public int getDestinationY() {
        return destinationY;
    }

    public void setDestinationY(int destinationY) {
        this.destinationY = destinationY;
    }

    protected UnitType unitType;
    protected int hitPoint;
    protected String mode = "standing";
    protected int speed;
    protected boolean beingHit = false;
    protected boolean hittingSomeOne = false;

    public boolean isBeingHit() {
        return beingHit;
    }

    public void setBeingHit(boolean beingHit) {
        this.beingHit = beingHit;
    }

    public boolean isHittingSomeOne() {
        return hittingSomeOne;
    }

    public void setHittingSomeOne(boolean hittingSomeOne) {
        this.hittingSomeOne = hittingSomeOne;
    }

    public void decreaseHitPoint(int damage) {
        this.hitPoint = hitPoint - damage;
    }

    protected boolean isBusy;

    public int getSpeed() {
        return speed;
    }

    public Unit(Kingdom homeland, UnitType unitType, int xPosition, int yPosition) {
        this.homeland = homeland;
        this.unitType = unitType;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.hitPoint = unitType.getHitPoint();
        this.speed = unitType.getMoveSpeed() * 10;
        isBusy = false;
        isOily = false;
    }

    public Unit(Kingdom kingdom, int xPosition, int yPosition) {
        this.homeland = kingdom;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        isBusy = false;
        isOily = false;
    }

    public UnitType getUnitType() {
        return unitType;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public Kingdom getHomeland() {
        return homeland;
    }

    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public boolean isBusy() {
        return isBusy;
    }

    public void setBusy(boolean busy) {
        isBusy = busy;
    }

    public String toString() {
        return this.getUnitType().getName() + " " + this.getHitPoint() + " " + this.getxPosition() + " " + this.getyPosition() + " " + this.getMode();
    }
}
