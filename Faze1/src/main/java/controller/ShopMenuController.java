package controller;

import model.Game;
import model.Property.*;

import static controller.GameController.GameMenuController.checkNumber;

public class ShopMenuController {

    private final Game game;

    public ShopMenuController(Game game) {
        this.game = game;
    }

    public String buyOrSell(String action, String name, String stringAmount) {

        String output = checkNumber(stringAmount);
        if (!output.equals("")) {
            return output;
        }
        int amount = Integer.parseInt(stringAmount);

        Property property = game.getCurrentKingdom().getPropertyByName(name);
        if (property != null) {
            if (amount > 0) {
                if (action.equals("buy")) {
                    property.addValue(amount);
                    if (property instanceof Food) {
                        if (((Food) property).getType().getBuyPrice() * amount <= game.getCurrentKingdom().getGold()) {
                            game.getCurrentKingdom().addToProperty(property);
                            game.getCurrentKingdom().addGold(-1 * ((Food) property).getType().getBuyPrice() * amount);
                            return action + " " + name + " successfully completed";
                        } else
                            return "Out of money";
                    } else if (property instanceof Weapon) {
                        if (((Weapon) property).getWeaponType().getBuyPrice() * amount <= game.getCurrentKingdom().getGold()) {
                            game.getCurrentKingdom().addToProperty(property);
                            game.getCurrentKingdom().addGold(-1 * ((Weapon) property).getWeaponType().getBuyPrice() * amount);
                            return action + " " + name + " successfully complected";
                        } else
                            return "Out of money";
                    } else if (property instanceof Resources) {
                        if (((Resources) property).getResourceType().getBuyPrice() * amount <= game.getCurrentKingdom().getGold()) {
                            game.getCurrentKingdom().addToProperty(property);
                            game.getCurrentKingdom().addGold(-1 * ((Resources) property).getResourceType().getBuyPrice() * amount);
                            return action + " " + name + " successfully completed";
                        } else
                            return "Out of money";
                    } else if (property instanceof DefensiveWeapon) {
                        if (((DefensiveWeapon) property).getDefenseType().getBuyPrice() * amount <= game.getCurrentKingdom().getGold()) {
                            game.getCurrentKingdom().addToProperty(property);
                            game.getCurrentKingdom().addGold(-1 * ((DefensiveWeapon) property).getDefenseType().getBuyPrice() * amount);
                        } else
                            return "Out of money";
                    }
                } else if (action.equals("sell")) {
                    property.addValue(-1 * amount);
                    if (property instanceof Food) {
                        if (amount <= game.getCurrentKingdom().getNumberOfProperties(property)) {
                            game.getCurrentKingdom().addToProperty(property);
                            game.getCurrentKingdom().addGold(((Food) property).getType().getSellPrice() * amount);
                            return action + " " + name + " successfully completed";
                        } else
                            return "You have not enough " + name;
                    } else if (property instanceof Weapon) {
                        if (amount <= game.getCurrentKingdom().getNumberOfProperties(property)) {
                            game.getCurrentKingdom().addToProperty(property);
                            game.getCurrentKingdom().addGold(((Weapon) property).getWeaponType().getSellPrice() * amount);
                            return action + " " + name + " successfully complected";
                        } else
                            return "You have not enough " + name;
                    } else if (property instanceof Resources) {
                        if (amount <= game.getCurrentKingdom().getNumberOfProperties(property)) {
                            game.getCurrentKingdom().addToProperty(property);
                            game.getCurrentKingdom().addGold(((Resources) property).getResourceType().getSellPrice() * amount);
                            return action + " " + name + " successfully completed";
                        } else
                            return "You have not enough " + name;
                    } else if (property instanceof DefensiveWeapon) {
                        if (amount <= game.getCurrentKingdom().getNumberOfProperties(property)) {
                            game.getCurrentKingdom().addToProperty(property);
                            game.getCurrentKingdom().addGold(((DefensiveWeapon) property).getDefenseType().getSellPrice() * amount);
                        } else
                            return "You have not enough " + name;
                    }
                }
                return "";
            } else
                return "Wrong amount";
        }

        return "Wrong name";
    }

    public String showPriceList() {
        String output = "";
        output += "Foods :\n";
        for (FoodType foodType : FoodType.values()) {
            Food food = new Food(foodType, 0);
            output += "\t" + foodType.name().toLowerCase() +
                    " : buy price = " + foodType.getBuyPrice() + ", sell price = " +
                    foodType.getSellPrice() + "\n";
        }

        output += "Resources :\n";
        for (ResourceType resourceType : ResourceType.values()) {
            output += "\t" + resourceType.name().toLowerCase() +
                    " : buy price = " + resourceType.getBuyPrice() + ", sell price = " + resourceType.getSellPrice() + "\n";
        }

        output += "Weapons :\n";
        for (WeaponType weaponType : WeaponType.values()) {
            output += "\t" + weaponType.name().toLowerCase() +
                    " : buy price = " + weaponType.getBuyPrice() + ", sell price = " + weaponType.getSellPrice() + "\n";
        }
        output += "Defence weapons :\n";
        for (DefenseType defenseType : DefenseType.values()) {
            output += "\t" + defenseType.name().toLowerCase() +
                    " : buy price = " + defenseType.getBuyPrice() + ", sell price = " + defenseType.getSellPrice() + "\n";
        }

        return output;
    }


}
