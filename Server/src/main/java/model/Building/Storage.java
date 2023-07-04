package model.Building;

import Enums.BuildingType;
import model.Kingdom;
import model.Property.*;

import java.util.ArrayList;
public class Storage extends Building {
    private BuildingType buildingType;

    private int capacity;

    private final ArrayList<Property> balance = new ArrayList<>();

    public Storage(BuildingType buildingType, Kingdom owner, int xPosition, int yPosition) {
        super(buildingType, owner, xPosition, yPosition);
        switch (buildingType) {
            case ARMOURY:
                capacity = 50;
                balance.add(new DefensiveWeapon(DefenseType.LEATHER_ARMOR, 1));
                balance.add(new DefensiveWeapon(DefenseType.METAL_ARMOR, 1));
                break;
            case STOCKPILE:
                capacity = 1000;
                for (ResourceType type : ResourceType.values()) {
                    balance.add(new Resources(type, 1));
                }
                break;
            case FOOD_STOCKPILE:
                capacity = 1000;
                for (FoodType type : FoodType.values()) {
                    balance.add(new Food(type, 1));
                }
                break;
            default:
                break;
        }
    }


    public boolean isFull() {
        int amount = 0;
        for (int i = 0; i < balance.size(); i++) {
            amount += balance.get(i).getValue();
        }
        return amount == capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getStored() {
        int amount = 0;
        for (int i = 0; i < balance.size(); i++) {
            amount += balance.get(i).getValue();
        }
        return amount;
    }

    public ArrayList<Property> getBalance() {
        return balance;
    }

    public void addToBalance(Property property) {
        for (Property myProperty : balance) {
            if (myProperty.equals(property)) {
                myProperty.addValue(property.getValue());
            }
        }
    }

    public Food getFoodByFoodType(FoodType foodType) {
        for (Property property : balance) {
            if (property instanceof Food)
                if (((Food) property).getType().name().equals(foodType.name()))
                    return ((Food) property);
        }
        return null;
    }
    public Weapon getWeaponByWeaponType(WeaponType weaponType) {
        for (Property property : balance) {
            if (property instanceof Weapon)
                if (((Weapon) property).getWeaponType().name().equals(weaponType.name()))
                    return ((Weapon) property);
        }
        return null;
    }

    public Resources getResourcesByResourceType(ResourceType resourceType) {
        for (Property property : balance) {
            if (property instanceof Resources)
                if (((Resources) property).getResourceType().name().equals(resourceType.name()))
                    return ((Resources) property);
        }
        return null;
    }
}
