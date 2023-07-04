package model.people;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Property.DefenseType;
import model.Property.WeaponType;

import java.awt.*;

public enum UnitType {
    //name, hitPoint, attackPower, defensePower, rangeFrom, rangeTo, moveSpeed, attackSpeed, attackAccuracy,


    ARCHER_BOW(400, 150, 0.17, 3, 11, 5, 80, 0.3, 0, WeaponType.BOW, null, false, false, true, "archer bow", "/images/Units/archerbow2.png"),
    ASSASSIN(600, 350, 0.35, 2, 3, 0, 105, 0, 0, null, null, true, false, true, "assassin", "/images/Units/assassin1.png"),
    SLAVE(250, 90, 0, 3, 1, 0, 10, 0, 0, null, null, false, true, true, "slave", "/images/Units/slaves1.png"),
    ARABIAN_SWORDSMAN(700, 270, 0.25, 4, 2, 0, 85, 0, 0, WeaponType.SWORD, DefenseType.LEATHER_ARMOR, false, false, true, "arabian swordsman", "/images/Units/arabianswordsmen1.png"),
    FIRE_THROWER(350, 250, 0.15, 4, 2, 0, 75, 0.1, 0, null, null, false, false, true, "fire throwers", "/images/Units/firethrower1.png"),
    SLINGER(300, 150, 0.15, 3, 2, 0, 17, 0.2, 0, null, null, false, false, true, "slinger", "/images/Units/slinger2.png"),
    HORSE_ARCHER(500, 170, 0.25, 5, 8, 3, 65, 0.5, 0, WeaponType.BOW, null, false, false, true, "horse archer", "/images/Units/horsearchers1.png"),


    CROSSBOW_MAN(450, 170, 0.25, 3, 5, 0, 25, 0.1, 0, WeaponType.CROSS_BOW, DefenseType.LEATHER_ARMOR, false, false, false, "crossbow man", "/images/Units/crossbowmen1.png"),
    PIKE_MAN(700, 250, 0.35, 2, 1, 0, 25, 0, 0, WeaponType.PIKE, DefenseType.METAL_ARMOR, false, false, false, "pike man", "/images/Units/pikeman1.png"),
    KING(1000, 210, 0.25, 2, 1, 0, 0, 0, 0, null, null, false, false, false, "king", "/images/Units/killarcher.png"),
    LADDER_MAN(280, 0, 0.1, 3, 1, 0, 11, 0, 2, null, null, true, false, false, "ladder man", "/images/Units/ladderman1.png"),
    MACE_MAN(550, 350, 0.25, 3, 1, 0, 25, 0, 0, WeaponType.MACE, DefenseType.LEATHER_ARMOR, true, false, false, "mace man", "/images/Units/maceman4.png"),
    SWORDS_MAN(350, 400, 0.32, 1, 2, 0, 45, 0, 3, WeaponType.SWORD, DefenseType.METAL_ARMOR, false, true, false, "swordsman", "/images/Units/swordsmen2.png"),
    KNIGHT(800, 450, 0.4, 4, 4, 0, 125, 0, 0, WeaponType.SWORD, DefenseType.METAL_ARMOR, false, false, false, "knight", "/images/Units/knight1.png"),
    TUNNELER(300, 150, 0.15, 3, 1, 0, 35, 0, 2, null, null, false, true, false, "tunneler", "/images/Units/tunneler1.png"),
    SOLDIER_ENGINEER(280, 150, 0.1, 2, 1, 0, 0, 0, 0, WeaponType.PETROLEUM, null, false, false, false, "oil engineer", "/images/Units/slingers1.png"),
    ARCHER(400, 150, 0.2, 4, 10, 4, 17, 0.3, 0, WeaponType.BOW, null, false, false, false, "archer", "/images/Units/archer3.png"),
    SPEAR_MAN(350, 230, 0.15, 3, 2, 0, 13, 0.1, 2, WeaponType.SPEAR, null, true, true, false, "spear man", "/images/Units/sperman1.png"),
    ENGINEER(280, 0, 0.1, 3, 0, 0, 35, 0, 3, null, null, false, true, false, "engineer", "/images/Units/engineer1.png"),
    BLACK_MONK(400, 150, 0.2, 1, 1, 0, 15, 0.2, 0, null, null, false, false, false, "black monk", "/images/Units/blackmonk1.png"),

    DOCTOR(300, 0, 0, 3, 0, 0,10,0,0,null, null, false,false,false, "doctor", "/images/Units/assassin4.png"),
    HORSE(100, 0, 0, 3, 0, 0, 0, 0, 0, null, null, false, false, false, "horse", "/images/Units/horsearchers1.png");
    private final int hitPoint;
    private final int attackPower;
    private final double defensePower;
    private final int moveSpeed;
    private int range;
    private int secondRange;
    private final int cost;
    private final double precision;
    private final int attackDelay;
    private final WeaponType weapon;
    private final DefenseType defence;
    private final boolean canClimb;
    private final boolean canDigDitch;
    private final boolean isArab;
    private final String name;

    private final String imagePath;


    UnitType(int hitPoint, int attackPower, double defensePower, int speed, int range, int secondRange, int cost,
             double precision, int attackDelay, WeaponType weapon, DefenseType defence, boolean canClimb, boolean canDigDitch, boolean isArab, String name, String path) {
        this.hitPoint = hitPoint;
        this.attackPower = attackPower;
        this.defensePower = defensePower;
        this.moveSpeed = speed;
        this.range = range;
        this.secondRange = secondRange;
        this.cost = cost;
        this.precision = precision;
        this.attackDelay = attackDelay;
        this.weapon = weapon;
        this.defence = defence;
        this.canClimb = canClimb;
        /*this.canThrowLadders = canThrowLadders;*/
        this.canDigDitch = canDigDitch;
        this.isArab = isArab;
        this.name = name;
        this.imagePath = path;
    }


    public int getHitPoint() {
        return hitPoint;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public DefenseType getDefence() {
        return defence;
    }

    public double getDefensePower() {
        return defensePower;
    }

    public double getPrecision() {
        return precision;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public int getMoveSpeed() {
        return moveSpeed;
    }

    public int getAttackDelay() {
        return attackDelay;
    }

    public int getRange() {
        return range;
    }

    public int getSecondRange() {
        return secondRange;
    }

    public WeaponType getWeapon() {
        return weapon;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public void setSecondRange(int secondRange) {
        this.secondRange = secondRange;
    }

    public boolean isArab() {
        return isArab;
    }

    public static UnitType getUnitTypeByName(String name) {
        if (name.charAt(0) == '\"') {
            name = name.substring(1, name.length() - 1);
        }
        for (UnitType type : UnitType.values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        return null;
    }
    public Image getImage() {
        return new javafx.scene.image.Image(Unit.class.getResource(imagePath).toExternalForm());
    }
}
