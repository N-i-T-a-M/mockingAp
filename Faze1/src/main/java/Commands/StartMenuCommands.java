package Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum StartMenuCommands {
    START_GAME("^start game$"),
    ADD_PLAYER("^add player -u (?<username>\\S*)$"),
    REMOVE_PLAYER("^remove player -u (?<username>\\S*)$"),
    REMOVE_ALL_PLAYERS("^remove all players$");
    private final String regex;

    StartMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, StartMenuCommands command) {
        return Pattern.compile(command.regex).matcher(input);
    }
}
