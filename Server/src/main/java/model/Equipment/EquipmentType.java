package model.Equipment;

public enum EquipmentType {
    //name, hitPoint, engineersCount, damage, range, speed, cost
    FIRE_BALLISTA("fire ballista", 500, 2, 400, 5, 2, 100, 1),
    BATTERING_RAM("battering ram", 300, 4, 500, 1, 1, 50, 2),
    SIEGE_TOWER("siege tower", 400, 4, 0, 0, 0, 150, 3),
    TREBUCHET("trebuchet", 800, 3, 400, 15, 0, 100, 3), //manjenigh sabet
    PORTABLE_SHIELD("portable shield", 200, 1, 0, 0, 0, 20, 2),
    CATAPULT("catapult", 600, 2, 250, 12, 1, 75, 1), //manjenigh moteharek


    ;
    private final String name;
    private final int hitPoint;
    private final int engineerCount;
    private final int damage;
    private final int range;
    private final int speed;
    private final int cost;

    private final int delay;

    EquipmentType(String name, int hitPoint, int engineerCount, int damage, int range, int speed, int cost, int delay) {
        this.name = name;
        this.hitPoint = hitPoint;
        this.engineerCount = engineerCount;
        this.damage = damage;
        this.range = range;
        this.speed = speed;
        this.cost = cost;
        this.delay = delay;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public int getRange() {
        return range;
    }

    public int getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }

    public int getEngineerCount() {
        return engineerCount;
    }

    public int getDamage() {
        return damage;
    }

    public int getSpeed() {
        return speed;
    }

    public int getDelay() {
        return delay;
    }

    public static EquipmentType getEquipmentTypeByName(String name) {
        for (EquipmentType equipmentType : EquipmentType.values()) {
            if (equipmentType.name.equals(name)) {
                return equipmentType;
            }
        }
        return null;
    }

}
