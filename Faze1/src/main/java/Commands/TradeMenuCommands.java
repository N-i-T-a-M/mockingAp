package Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum TradeMenuCommands {
    SHOW_ALL_TRADES("^trade list$"),
    TRADE("(?=.*-t)(?=.*-a)(?=.*-p)(?=.*-m)(?=.*-r)^trade(( -t (?<type>\\S+))|( -a (?<amount>[-]?\\d+))|( -p (?<price>[-]?\\d+))|( -m (?<message>.+))|( -r (?<receiverName>\\S+))){5}$"),
    TRADE_HISTORY("trade history"),
    TRADE_ACCEPT("(?=.*-i)(?=.*-m)^trade accept(( -i (?<id>\\S+))|( -m (?<message>.+))){2}$");
    private final String regex;

    TradeMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, TradeMenuCommands command) {
        return Pattern.compile(command.regex).matcher(input);
    }
}
