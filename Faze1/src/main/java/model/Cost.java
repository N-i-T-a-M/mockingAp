package model;

public class Cost {
    private final int gold;
    private final int wood;
    private final int iron;
    private final int stone;
    private final int pitch;

    public Cost(int gold, int wood, int iron, int stone, int pitch) {
        this.gold = gold;
        this.wood = wood;
        this.iron = iron;
        this.stone = stone;
        this.pitch = pitch;
    }

    public int getGold() {
        return gold;
    }

    public int getWood() {
        return wood;
    }

    public int getIron() {
        return iron;
    }

    public int getStone() {
        return stone;
    }

    public int getPitch() {
        return pitch;
    }
}
