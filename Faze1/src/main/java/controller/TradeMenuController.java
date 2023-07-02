package controller;

import model.Game;
import model.Kingdom;
import model.Property.DefensiveWeapon;
import model.Property.Food;
import model.Property.Resources;
import model.Property.Weapon;
import model.Trade;

import java.util.ArrayList;

import static controller.GameController.GameMenuController.checkNumber;

public class TradeMenuController {

    private final Game game;

    public TradeMenuController(Game game) {
        this.game = game;
    }

    public String showAllTrades() {

        String output = "";

        output += "id \\\\ resource name \\\\ amount \\\\ price \\\\ sender \\\\ receiver \\\\ massage" + "\n";
        int i = 1;
        if (!game.getCurrentKingdom().getTrades().isEmpty()) {
            for (Trade trade : game.getCurrentKingdom().getTrades()) {
                output += i + " \\\\ ";
                if (trade.getProperty() instanceof Food)
                    output += ((Food) trade.getProperty()).getType().name().toLowerCase();
                else if (trade.getProperty() instanceof Resources)
                    output += ((Resources) trade.getProperty()).getResourceType().name().toLowerCase();
                else if (trade.getProperty() instanceof Weapon)
                    output += ((Weapon) trade.getProperty()).getWeaponType().name().toLowerCase();
                else if (trade.getProperty() instanceof DefensiveWeapon)
                    output += ((DefensiveWeapon) trade.getProperty()).getDefenseType().name().toLowerCase();
                output += " \\\\ " + trade.getResourceAmount() + " \\\\ " + trade.getPrice() +
                        " \\\\ " + trade.getSender().getOwner().getUsername() + " \\\\ " +
                        trade.getReceiver().getOwner().getUsername() + " \\\\ " +
                        trade.getSenderMessage() + "\n";
            }
        }
        return output;
    }

    public String trade(String resourceType, String stringRecourseAmount, String stringPrice, String message, String receiverName) {
        String output = checkNumber(stringRecourseAmount);
        if (!output.equals("")) {
            return output;
        }
        output = checkNumber(stringPrice);
        if (!output.equals("")) {
            return output;
        }
        int resourceAmount = Integer.parseInt(stringRecourseAmount);
        int price = Integer.parseInt(stringPrice);
        if (game.getCurrentKingdom().getPropertyByName(resourceType) != null) {
            if (getUserByNameInThisGame(receiverName) != null) {
                if (resourceAmount > 0) {
                    if (price >= 0) {
                        if (price <= game.getCurrentKingdom().getGold()) {
                            game.getCurrentKingdom().setTrades(new Trade(game.getCurrentKingdom().getPropertyByName(resourceType),
                                    resourceAmount, price, message, game.getCurrentKingdom(), getUserByNameInThisGame(receiverName)));
                            getUserByNameInThisGame(receiverName).setTrades(game.getCurrentKingdom().getTrades().get(game.getCurrentKingdom().getTrades().size() - 1));
                            return "trade added successfully";
                        } else
                            return "you have not enough money";
                    } else
                        return "wrong price";
                } else
                    return "wrong resource amount";
            } else
                return "User " + receiverName + "isn't in this game";
        } else
            return "there's no " + resourceType;
    }

    public String acceptTrade(String id, String message) {
        String output = checkNumber(id);
        if (!output.equals("")) {
            return output;
        }
        int tradeId = Integer.parseInt(id);
        if (tradeId <= game.getCurrentKingdom().getTrades().size() &&
                tradeId > 0) {
            if (!game.getCurrentKingdom().equals(game.getCurrentKingdom().getTrades().get(tradeId - 1).getSender())) {
                game.getCurrentKingdom().getTrades().get(tradeId - 1).doTrade(message);
                return "trade accepted";
            } else
                return "You can't accept trade that you create it";
        } else
            return "wrong trade ID";
    }

    public String showTradeHistory() {
        String output = "";
        output += "resource name \\\\ amount \\\\ price \\\\ sender \\\\ receiver \\\\ is accepted \\\\ sender massage \\\\ receiver message" + "\n";
        int i = 1;
        for (Trade trade : game.getCurrentKingdom().getTradesHistory()) {
            output += i + " \\\\ ";
            if (trade.getProperty() instanceof Food)
                output += ((Food) trade.getProperty()).getType().name().toLowerCase();
            else if (trade.getProperty() instanceof Resources)
                output += ((Resources) trade.getProperty()).getResourceType().name().toLowerCase();
            else if (trade.getProperty() instanceof Weapon)
                output += ((Weapon) trade.getProperty()).getWeaponType().name().toLowerCase();
            output += " \\\\ " + trade.getResourceAmount() + " \\\\ " + trade.getPrice() +
                    " \\\\ " + trade.getSender().getOwner().getUsername() + " \\\\ " +
                    trade.getReceiver().getOwner().getUsername() + " \\\\ " + trade.isAccepted() + " \\\\ " +
                    trade.getSenderMessage() + " \\\\ " + trade.getReceiverMessage() + "\n";
        }
        return output;
    }

    public void deleteTrades() {
        ArrayList<Trade> trades = game.getCurrentKingdom().getTrades();
        for (int i = 0; i < trades.size(); ) {
            if (!(trades.get(i).getSender().equals(game.getCurrentKingdom()))) {
                trades.get(i).getSender().getTrades().remove(trades.get(i));
                trades.get(i).getSender().setTradesHistory(trades.get(i));
                trades.get(i).getReceiver().setTradesHistory(trades.get(i));
                trades.get(i).getReceiver().getTrades().remove(trades.get(i));
            } else
                i++;
        }
    }

    private Kingdom getUserByNameInThisGame(String name) {
        for (Kingdom kingdom : game.getPlayers())
            if (kingdom.getOwner().getUsername().equals(name))
                return kingdom;
        return null;
    }
}
