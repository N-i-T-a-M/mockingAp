package Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum RegisterMenuCommands {
    USER_CREATE("(?=.*-p){1}(?=.*-u){1}(?=.*--email){1}(?=.*-n){1}(?=.*-s)?^user create(( -u (?<username>\\S*))|( -p (?<password>\\S*) (?<passwordRepeat>\\S*))|( -s (?<slogan>(\"[^\"]*\")|\\S*))|( --email (?<email>\\S*))|( -n (?<nickname>(\"[^\"]*\")|\\S*))){4,5}$"),
    QUESTION_PICK("(?=.*-q){1}(?=.*-a){1}(?=.*-c){1}^question pick(( -q (?<questionNumber>[-]?\\d*))|( -a (?<answer>(\"[^\"]*\")|\\S*))|( -c (?<answerConfirm>(\"[^\"]*\")|\\S*))){3}$");
    private final String regex;

    RegisterMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, RegisterMenuCommands command) {
        return Pattern.compile(command.regex).matcher(input);
    }
}
