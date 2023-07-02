package model.Property;

public class Weapon extends Property {

    private final WeaponType weaponType;

    public Weapon(WeaponType weaponType, int value) {
        super(value);
        this.weaponType = weaponType;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

}
