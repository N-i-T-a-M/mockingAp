package model.Property;

public class DefensiveWeapon extends Property {

    private final DefenseType defenseType;

    public DefensiveWeapon(DefenseType defenseType, int value) {
        super(value);
        this.defenseType = defenseType;
    }

    public DefenseType getDefenseType() {
        return defenseType;
    }
}
