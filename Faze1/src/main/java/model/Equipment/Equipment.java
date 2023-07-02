package model.Equipment;

import model.Kingdom;
import model.people.Unit;

public class Equipment extends Unit {
    private final EquipmentType equipmentType;

    public Equipment(EquipmentType equipmentType, int xPosition, int yPosition, Kingdom owner) {
        super(owner, xPosition, yPosition);
        this.equipmentType = equipmentType;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.homeland = owner;
        hitPoint = equipmentType.getHitPoint();
    }

    public EquipmentType getEquipmentType() {
        return equipmentType;
    }
}
