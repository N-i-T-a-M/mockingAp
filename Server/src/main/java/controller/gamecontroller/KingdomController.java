package controller.gamecontroller;

import model.Kingdom;
import model.Property.Food;
import model.Property.FoodType;

public class KingdomController {
    private final Kingdom currentKingdom;

    public KingdomController(Kingdom currentKingdom) {
        this.currentKingdom = currentKingdom;
    }

    public String setFoodRate(String number) throws NumberFormatException {
        int rate;
        try {
            rate = Integer.parseInt(number);
        } catch (NumberFormatException e) {
            return "Input is not a number";
        }
        if (rate < -2 || rate > 2) {
            return "Invalid rate";
        }
        Food food = new Food(FoodType.APPLES, rate);
        int popularity = currentKingdom.getPopularity();
        if (currentKingdom.getNumberOfProperties(food) < 0 && rate > -2) {
            currentKingdom.setFoodRate(-2);
            currentKingdom.setPopularity(popularity + 4 * (-2));
            return "Not enough food";
        }
        currentKingdom.setFoodRate(rate);
        currentKingdom.setPopularity(popularity + 4 * rate);
        return "Food rate set successfully";
    }

    public String setTaxRate(String number) throws NumberFormatException {
        int rate;
        try {
            rate = Integer.parseInt(number);
        } catch (NumberFormatException e) {
            return "Input is not a number";
        }
        if (rate < -3 || rate > 8) {
            return "Invalid rate";
        }
        int popularity = currentKingdom.getPopularity();
        if (currentKingdom.getGold() == 0) {
            currentKingdom.setTaxRate(0);
            currentKingdom.setPopularity(popularity + 1);
            return "You don't have enough gold, come back later";
        }
        currentKingdom.setTaxRate(rate);
        if (rate <= 0) {
            currentKingdom.setPopularity(popularity + rate * (-2) + 1);
        } else if (rate <= 4) {
            currentKingdom.setPopularity(popularity + (-2) * rate);
        } else {
            currentKingdom.setPopularity(popularity + (-4) * rate + 8);
        }
        return "Tax rate set successfully";
    }

    public String showTaxRate() {
        return "Your tax rate is: " + currentKingdom.getTaxRate();
    }

    public String setFearRate(String number) throws NumberFormatException {//positive fear -> negative popularity
        int rate;
        if (number.isEmpty()) {
            return "rate can't be empty";
        }
        try {
            rate = Integer.parseInt(number);
        } catch (NumberFormatException e) {
            return "Input is not a number";
        }
        if (rate < -5 || rate > 5) {
            return "Invalid rate";
        }
        int popularity = currentKingdom.getPopularity();
        currentKingdom.setFearRate(rate);
        currentKingdom.setPopularity(popularity - rate);
        return "Fear rate set successfully";
    }

    public String showPopularityFactors() {
        return "Your popularity factors are: \n" +
                "Food rate: " + currentKingdom.getFoodRate() + "\n" +
                "Tax rate: " + currentKingdom.getTaxRate() + "\n" +
                "Fear rate: " + currentKingdom.getFearRate() + "\n" +
                "Religious people: " + currentKingdom.getReligiousPeople();
    }

    public String showPopularity() {
        return "Your popularity is: " + currentKingdom.getPopularity();
    }

    public String showFoodList() {
        Food bread = new Food(FoodType.BREAD, 0);
        Food apples = new Food(FoodType.APPLES, 0);
        Food cheese = new Food(FoodType.CHEESE, 0);
        Food meat = new Food(FoodType.MEAT, 0);
        return "Your food list is:\n" +
                "1) bread:" + currentKingdom.getNumberOfProperties(bread) + "\n" +
                "2) apples:" + currentKingdom.getNumberOfProperties(apples) + "\n" +
                "3) cheese:" + currentKingdom.getNumberOfProperties(cheese) + "\n" +
                "4) meat:" + currentKingdom.getNumberOfProperties(meat);
    }

    public String showFoodRate() {
        return "Your food rate is: " + currentKingdom.getFoodRate();
    }
}
