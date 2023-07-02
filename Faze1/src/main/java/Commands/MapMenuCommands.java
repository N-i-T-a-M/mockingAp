package Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MapMenuCommands {
    SET_TEXTURE_OF_CELL("(?=.*-x)(?=.*-y)(?=.*-t)^settexture(( -x (?<xCoordinate>[-]?\\d*))|( -y (?<yCoordinate>[-]?\\d*))|( -t (?<type>\\S*))){3}$"),
    SET_TEXTURE_OF_CELLS("(?=.*-x1)(?=.*-y1)(?=.*-x2)(?=.*-y2)(?=.*-t)^settexture(( -x1 (?<x1Coordinate>[-]?\\d*))|( -y1 (?<y1Coordinate>[-]?\\d*))|( -x2 (?<x2Coordinate>[-]?\\d*))|( -y2 (?<y2Coordinate>[-]?\\d*))|( -t (?<type>\\S*))){5}$"),
    CLEAR_CELL("(?=.*-x)(?=.*-y)^clear(( -x (?<xCoordinate>[-]?[-]?\\d*))|( -y (?<yCoordinate>[-]?\\d*))){2}$"),
    DROP_ROCK("(?=.*-x)(?=.*-y)(?=.*-d)^droprock(( -x (?<xCoordinate>[-]?\\d*))|( -y (?<yCoordinate>[-]?\\d*))|( -d (?<direction>(n|e|w|s|r)))){3}$"),
    DROP_TREE("(?=.*-x)(?=.*-y)(?=.*-t)^droptree(( -x (?<xCoordinate>[-]?\\d*))|( -y (?<yCoordinate>[-]?\\d*))|( -t (?<type>\\S*))){3}$"),
    SHOW_DETAILS("^show detail -x (?<x>[-]?\\d*) -y (?<y>[-]?\\d*)$"),
    MAP_UP("(?=.*-d)(?=.*-n)^map up(( -d (?<direction>[news]*))|( -n (?<number>[-]?\\d*))){2}$"),
    SHOW_MAP("(?=.*-x)(?=.*-y)^show map(( -x (?<x>[-]?\\d*))|( -y (?<y>[-]?\\d*))){2}$"),
    SELECT_MAP("^select this map$");
    private final String regex;

    MapMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, MapMenuCommands command) {
        return Pattern.compile(command.regex).matcher(input);
    }
}
