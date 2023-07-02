package Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LoginMenuCommands {
    USER_LOGIN("(?=.*-u)(?=.*-p)(?=.*--stay-logged-in)?^user login(( -u (?<username>\\S*))|( -p (?<password>\\S*))|(?<stay> --stay-logged-in)){2,3}"),
    FORGOT_MY_PASSWORD("^forgot my password -u (?<username>\\S*)$"),
    NEW_PASSWORD("^new password -p (?<password>\\S*) (?<passwordRepeat>\\S*)$");
    private final String regex;

    LoginMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, LoginMenuCommands command) {
        return Pattern.compile(command.regex).matcher(input);
    }
}
