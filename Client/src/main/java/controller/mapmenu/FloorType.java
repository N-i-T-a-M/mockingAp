package controller.mapmenu;


import Enums.TextureType;

import java.util.regex.Pattern;

public enum FloorType {
    EARTH("^earth$", TextureType.EARTH),
    GRAVEL("^gravel$", TextureType.GRAVEL),
    STONE_MINE("^stoneMine$", TextureType.STONE_MINE),
    ROCK_NORTH("^rockN$", TextureType.ROCK_NORTH),
    ROCK_EAST("^rockE$", TextureType.ROCK_EAST),
    ROCK_WEST("^rockW$", TextureType.ROCK_WEST),
    ROCK_SOUTH("^rockS$", TextureType.ROCK_SOUTH),
    IRON_MINE("^ironMine$", TextureType.IRON_MINE),
    GRASS("^grass$", TextureType.GRASS),
    MEADOW("^meadow$", TextureType.MEADOW),
    DENSE_MEADOW("^denseMeadow$", TextureType.DENSE_MEADOW),


    OIL("^oil$", TextureType.OIL),
    PLAIN("^plain$", TextureType.PLAIN),
    SHALLOW_WATER("^shallowWater$", TextureType.SHALLOW_WATER),
    FOUNTAIN("^fountain$", TextureType.FOUNTAIN),
    SMALL_POND("^smallPond$", TextureType.SMALL_POND),
    BIG_POND("^bigPond$", TextureType.BIG_POND),
    BEACH("^beach$", TextureType.BEACH),
    SEA("^sea$", TextureType.SEA);

    private final String regex;

    private final TextureType type;


    FloorType(String regex, TextureType type) {
        this.regex = regex;
        this.type = type;
    }

    public static boolean hasMatcher(String string, FloorType floorType) {
        return Pattern.compile(floorType.regex).matcher(string).find();
    }

    public TextureType getType() {
        return type;
    }
}
