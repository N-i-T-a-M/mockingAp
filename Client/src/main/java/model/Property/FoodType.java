package model.Property;

public enum FoodType {
    BREAD(9),
    MEAT(10),
    CHEESE(8),
    APPLES(11);

    private final int buyPrice;


    FoodType(int buyPrice) {
        this.buyPrice = buyPrice;
    }
    public static int getBuyPrice (FoodType type) {
        return type.buyPrice;
    }
    public static int getSellPrice (FoodType type) {
        return type.buyPrice * 8 / 10;
    }
    public int getBuyPrice() {
        return buyPrice;
    }

    public int getSellPrice() {
        return buyPrice * 8 / 10;
    }
}
