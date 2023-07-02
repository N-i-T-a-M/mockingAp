package org.example;

public enum Type {
    EARTH(true),
    GRAVEL(true),
    STONE_MINE(true),
    ROCK_NORTH(false),
    ROCK_EAST(false),
    ROCK_WEST(false),
    ROCK_SOUTH(false),
    IRON_MINE(true),
    GRASS(true),
    MEADOW(true),
    DENSE_MEADOW(true),


    OIL(true),
    PLAIN(true),
    SHALLOW_WATER(true),
    FOUNTAIN(false),
    SMALL_POND(false),
    BIG_POND(false),
    BEACH(true),
    SEA(false);

    private final boolean passable;

    Type(boolean passable) {
        this.passable = passable;
    }
}
