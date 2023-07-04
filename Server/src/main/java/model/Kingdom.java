package model;

import model.Building.Building;
import model.Building.ProductionCenter;
import model.Building.Storage;
import model.Building.UnitCreation;
import model.Equipment.Equipment;
import model.Property.*;
import model.map.Cell;
import model.people.Unit;
import model.people.UnitType;
import model.people.soldier.Soldier;

import java.util.ArrayList;

public class Kingdom {


    private int population;

    private int unEmployed;
    private final ArrayList<Trade> trades = new ArrayList<>();

    private final ArrayList<Trade> tradesHistory = new ArrayList<>();
    private ArrayList<Soldier> engineers = new ArrayList<Soldier>();
    private ArrayList<Unit> units = new ArrayList<Unit>();

    private ArrayList<Soldier> soldiers = new ArrayList<Soldier>();

    private ArrayList<Storage> stables = new ArrayList<Storage>();

    private ArrayList<Storage> weapons = new ArrayList<>();

    private ArrayList<Storage> defensiveWeapons = new ArrayList<>();

    private ArrayList<Storage> stockPiles = new ArrayList<>();

    private ArrayList<Storage> foodStockPiles = new ArrayList<>();

    private User owner;
    private int gold;
    private int fearRate;
    private int texRate;
    private int foodRate;

    private final ArrayList<Equipment> equipments = new ArrayList<>();


    private final ArrayList<ProductionCenter> allProductionCenters = new ArrayList<>();

    private final ArrayList<UnitCreation> allUnitCreations = new ArrayList<>();

    private final ArrayList<Building> siegeBuildings = new ArrayList<>();

    private final Cell HeadSquare;
    private int numberOfWorkers;

    private int taxRate = 0;
    private int popularity;
    private int religiousPeople = 0;
    private final Unit king;

    public Unit getKing() {
        return king;
    }

    public int getReligiousPeople() {
        return religiousPeople;
    }

    public void setReligiousPeople(int religiousPeople) {
        this.religiousPeople = religiousPeople;
    }

    public int getPopularity() {
        return popularity;
    }

    public Kingdom(User owner, Cell headSquare) {
        this.owner = owner;
        gold = 500;
        fearRate = 0;
        taxRate = 0;
        popularity = 100;
        foodRate = 0;
        population = 15;
        unEmployed = 15;
        numberOfWorkers = 0;
        this.HeadSquare = headSquare;
        this.king = new Unit(this, UnitType.KING, this.getHeadSquare().getxCoordinate(), this.getHeadSquare().getyCoordinate());
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public int getFearRate() {
        return fearRate;
    }

    public void setFearRate(int fearRate) {
        this.fearRate = fearRate;
    }

    public int getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(int taxRate) {
        this.taxRate = taxRate;
    }

    public int getFoodRate() {
        return foodRate;
    }

    public void setFoodRate(int foodRate) {
        this.foodRate = foodRate;
    }

    public double getGold() {
        return gold;
    }

    public User getOwner() {
        return owner;
    }


    public ArrayList<UnitCreation> getAllUnitCreations() {
        return allUnitCreations;
    }

    public void addToAllUnitCreations(UnitCreation unitCreation) {
        allUnitCreations.add(unitCreation);
    }

    public void addToStockPiles(Storage storage) {
        stockPiles.add(storage);

    }

    public void addToFoodStockPiles(Storage storage) {
        foodStockPiles.add(storage);
    }

    public void addToStables(Storage storage) {
        stables.add(storage);
    }


    public int getPopulation() {
        return population;
    }

    public void addPopulation(int amount) {
        population += amount;
    }

    public void addEngineer(Soldier engineer) {
        engineers.add(engineer);
    }

    public void addSoldier(Soldier soldier) {
        soldiers.add(soldier);
    }

    public void addUnit(Unit unit) {
        units.add(unit);
    }

    public Cell getHeadSquare() {
        return HeadSquare;
    }

    public int getAllHorses() {
        int total = 0;
        for (Storage storage : stables) {
            total += storage.getStored();
        }
        return total;
    }

    public int getAllWeaponByWeaponType(WeaponType weaponType) {
        int total = 0;
        for (Storage storage : weapons) {
            for (int i = 0; i < storage.getBalance().size(); i++) {
                if (((Weapon) storage.getBalance().get(i)).getWeaponType() == weaponType) {
                    total += storage.getBalance().get(i).getValue();
                }
            }

        }
        return total;
    }

    public int getAllDefensiveWeaponByDefenseType(DefenseType defenseType) {
        int total = 0;
        for (Storage storage : defensiveWeapons) {
            for (int i = 0; i < storage.getBalance().size(); i++) {
                if (((DefensiveWeapon) storage.getBalance().get(i)).getDefenseType() == defenseType) {
                    total += storage.getBalance().get(i).getValue();
                }
            }
        }
        return total;
    }

    public void addGold(int amount) {
        gold += amount;
    }

    public ArrayList<Soldier> getEngineers() {
        return engineers;
    }

    public ArrayList<Soldier> getSoldiers() {
        return soldiers;
    }

    public ArrayList<Storage> getDefensiveWeapons() {
        return defensiveWeapons;
    }

    public ArrayList<Storage> getStables() {
        return stables;
    }

    public ArrayList<Storage> getWeapons() {
        return weapons;
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public int getUnemployed() {
        return unEmployed;
    }

    public void decreaseWeapons(Weapon weapon) {
        for (Storage storage : weapons) {
            if (weapon.getValue() > 0) {
                for (Property property : storage.getBalance()) {
                    if (((Weapon) property).getWeaponType() == weapon.getWeaponType()) {
                        property.addValue(-Math.min(weapon.getValue(), property.getValue()));
                        weapon.addValue(-property.getValue());
                    }
                }
            }

        }
    }

    public void decreaseDefensiveWeapons(DefensiveWeapon defensiveWeapon) {
        for (Storage storage : defensiveWeapons) {
            if (defensiveWeapon.getValue() > 0) {
                for (Property property : storage.getBalance()) {
                    if (((DefensiveWeapon) property).getDefenseType() == defensiveWeapon.getDefenseType()) {
                        property.addValue(-defensiveWeapon.getValue());
                        defensiveWeapon.addValue(-property.getValue());

                    }
                }
            }

        }
    }


    public int getNumberOfProperties(Property property) {
        if (property instanceof Resources) {
            return getNumberOfResources(((Resources) property));
        }
        if (property instanceof Weapon) {
            return getNumberOfWeapon(((Weapon) property));
        }
        if (property instanceof DefensiveWeapon) {
            return getNumberOfDefensiveWeapon(((DefensiveWeapon) property));
        }
        if (property instanceof Food) {
            return getNumberOfFood(((Food) property));
        }
        return -2;
    }

    private int getNumberOfResources(Resources resource) {
        int amount = 0;
        for (Storage storage : stockPiles) {
            for (Property property : storage.getBalance()) {
                if (((Resources) property).getResourceType() == resource.getResourceType()) {
                    amount += property.getValue();
                }
            }
        }
        return amount;
    }

    private int getNumberOfWeapon(Weapon weapon) {
        int amount = 0;
        for (Storage storage : weapons) {
            for (Property property : storage.getBalance()) {
                if (((Weapon) property).getWeaponType() == weapon.getWeaponType()) {
                    amount += property.getValue();
                }
            }
        }
        return amount;

    }

    private int getNumberOfDefensiveWeapon(DefensiveWeapon defensiveWeapon) {
        int amount = 0;
        for (Storage storage : defensiveWeapons) {
            for (Property property : storage.getBalance()) {
                if (((DefensiveWeapon) property).getDefenseType() == defensiveWeapon.getDefenseType()) {
                    amount += property.getValue();
                }
            }
        }
        return amount;
    }

    private int getNumberOfFood(Food food) {
        int amount = 0;
        for (Storage storage : foodStockPiles) {
            for (Property property : storage.getBalance()) {
                if (((Food) property).getType() == food.getType()) {
                    amount += property.getValue();
                }
            }
        }
        return amount;
    }

    public void addToProperty(Property property) {
        if (property instanceof Resources) {
            addToResources((Resources) property);
        }
        if (property instanceof Weapon) {
            addToWeapon((Weapon) property);
        }
        if (property instanceof DefensiveWeapon) {
            addToDefensiveWeapon((DefensiveWeapon) property);
        }
        if (property instanceof Food) {
            addToFood((Food) property);
        }
    }

    private void addToFood(Food food) {
        for (Storage storage : foodStockPiles) {
            if (food.getValue() != 0) {
                for (Property property : storage.getBalance()) {
                    if (((Food) property).getType() == food.getType()) {
                        property.addValue(Math.min(storage.getCapacity() - storage.getStored(), food.getValue()));
                        food.addValue(-Math.min(storage.getCapacity() - storage.getStored(), food.getValue()));
                    }
                }
            }
        }
    }

    private void addToDefensiveWeapon(DefensiveWeapon defensiveWeapon) {
        for (Storage storage : defensiveWeapons) {
            if (defensiveWeapon.getValue() != 0) {
                for (Property property : storage.getBalance()) {
                    if (((DefensiveWeapon) property).getDefenseType() == defensiveWeapon.getDefenseType()) {
                        property.addValue(Math.min(storage.getCapacity() - storage.getStored(), defensiveWeapon.getValue()));
                        defensiveWeapon.addValue(-Math.min(storage.getCapacity() - storage.getStored(), defensiveWeapon.getValue()));
                    }
                }
            }
        }
    }

    private void addToWeapon(Weapon weapon) {
        for (Storage storage : weapons) {
            if (weapon.getValue() != 0) {
                for (Property property : storage.getBalance()) {
                    if (((Weapon) property).getWeaponType() == weapon.getWeaponType()) {
                        property.addValue(Math.min(storage.getCapacity() - storage.getStored(), weapon.getValue()));
                        weapon.addValue(-Math.min(storage.getCapacity() - storage.getStored(), weapon.getValue()));
                    }
                }
            }
        }
    }

    private void addToResources(Resources resource) {
        for (Storage storage : stockPiles) {
            if (resource.getValue() != 0) {
                for (Property property : storage.getBalance()) {
                    if (((Resources) property).getResourceType() == resource.getResourceType()) {
                        property.addValue(Math.min(storage.getCapacity() - storage.getStored(), resource.getValue()));
                        resource.addValue(-Math.min(storage.getCapacity() - storage.getStored(), resource.getValue()));
                    }
                }
            }
        }
    }


    public void spendProperties(Property property) {
        if (property instanceof Resources) {
            spendResources((Resources) property);
        }
        if (property instanceof Weapon) {
            spendWeapon((Weapon) property);
        }
        if (property instanceof DefensiveWeapon) {
            spendDefensiveWeapon((DefensiveWeapon) property);
        }
        if (property instanceof Food) {
            spendFood((Food) property);
        }
    }

    private void spendFood(Food food) {
        for (Storage storage : foodStockPiles) {
            if (food.getValue() > 0) {
                for (Property property : storage.getBalance()) {
                    if (((Food) property).getType() == food.getType()) {
                        int keepResource = food.getValue();
                        food.addValue(-Math.min(property.getValue(), food.getValue()));
                        property.addValue(-Math.min(property.getValue(), keepResource));
                    }
                }
            }

        }
    }

    private void spendDefensiveWeapon(DefensiveWeapon defensiveWeapon) {
        for (Storage storage : defensiveWeapons) {
            if (defensiveWeapon.getValue() > 0) {
                for (Property property : storage.getBalance()) {
                    if (((DefensiveWeapon) property).getDefenseType() == defensiveWeapon.getDefenseType()) {
                        int keepResource = defensiveWeapon.getValue();
                        defensiveWeapon.addValue(-Math.min(property.getValue(), defensiveWeapon.getValue()));
                        property.addValue(-Math.min(property.getValue(), keepResource));
                    }
                }
            }

        }
    }

    private void spendWeapon(Weapon weapon) {
        for (Storage storage : weapons) {
            if (weapon.getValue() > 0) {
                for (Property property : storage.getBalance()) {
                    if (((Weapon) property).getWeaponType() == weapon.getWeaponType()) {
                        int keepResource = weapon.getValue();
                        weapon.addValue(-Math.min(property.getValue(), weapon.getValue()));
                        property.addValue(-Math.min(property.getValue(), keepResource));
                    }
                }
            }

        }
    }

    private void spendResources(Resources resource) {
        for (Storage storage : stockPiles) {
            if (resource.getValue() > 0) {
                for (Property property : storage.getBalance()) {
                    if (((Resources) property).getResourceType() == resource.getResourceType()) {
                        int keepResource = resource.getValue();
                        resource.addValue(-Math.min(property.getValue(), resource.getValue()));
                        property.addValue(-Math.min(property.getValue(), keepResource));
                    }
                }
            }

        }
    }

    public static Property getPropertyByName(String name) {
        for (FoodType foodType : FoodType.values()) {
            if (foodType.name().toLowerCase().equals(name))
                return new Food(foodType, 0);
        }

        for (WeaponType weaponType : WeaponType.values()) {
            if (weaponType.name().toLowerCase().equals(name))
                return new Weapon(weaponType, 0);
        }

        for (ResourceType resourceType : ResourceType.values()) {
            if (resourceType.name().toLowerCase().equals(name))
                return new Resources(resourceType, 0);
        }
        for (DefenseType defenseType : DefenseType.values()) {
            if (defenseType.name().toLowerCase().equals(name))
                return new DefensiveWeapon(defenseType, 0);
        }
        return null;
    }

    public ArrayList<Trade> getTradesHistory() {
        return tradesHistory;
    }

    public void setTradesHistory(Trade trades) {
        this.tradesHistory.add(trades);
    }

    public ArrayList<Trade> getTrades() {
        return trades;
    }

    public void setTrades(Trade trades) {
        this.trades.add(trades);
    }

    public ArrayList<Storage> getFoodStockPiles() {
        return foodStockPiles;
    }

    public ArrayList<Storage> getStockPiles() {
        return stockPiles;
    }

    public void addEquipment(Equipment equipment) {
        equipments.add(equipment);
    }

    public ArrayList<Equipment> getEquipments() {
        return equipments;
    }

    public ArrayList<ProductionCenter> getAllProductionCenters() {
        return allProductionCenters;
    }

    public void addProductionCenter(ProductionCenter building) {
        allProductionCenters.add(building);
    }

    public void addPopularity(int amount) {
        popularity += amount;
    }

    public void addUnEmployed(int amount) {
        unEmployed += amount;
    }

    public void addToWeapons(Storage storage) {
        weapons.add(storage);
    }

    public void addToDefensiveWeapon(Storage storage) {
        defensiveWeapons.add(storage);
    }

    public ArrayList<Building> getSiegeBuildings() {
        return siegeBuildings;
    }

    public void addToSiegeBuildings(Building siege) {
        siegeBuildings.add(siege);
    }

    public void removeFromSiegeBuildings(Building siege) {
        siegeBuildings.remove(siege);
    }

    public int getNumberOfWorkers() {
        return numberOfWorkers;
    }

    public void setNumberOfWorkers(int numberOfWorkers) {
        this.numberOfWorkers = numberOfWorkers;
    }
}
