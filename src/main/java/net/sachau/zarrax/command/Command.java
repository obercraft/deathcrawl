package net.sachau.zarrax.command;

import net.sachau.zarrax.Logger;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.effect.Exhausted;
import net.sachau.zarrax.card.keyword.Keyword;
import net.sachau.zarrax.engine.GameEngine;
import net.sachau.zarrax.engine.Player;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;
import java.util.List;

public class Command {

    public static CommandType getType(String command) {
        if (StringUtils.isNotEmpty(command)) {
            List<String> args = getArguments(command);
            if (args.size() > 0) {
                Commands c = Commands.valueOf(args.get(0));
                if (c != null) {
                    return c.getStatus().getType();
                }
            }
        }
        return CommandType.ACTION;
    }

    public static List<String> getArguments(String command) {
        List<String> args = new LinkedList<>();
        if (StringUtils.isNotEmpty(command)) {
            for (String cmd : command.split(" ", -1)) {
                args.add(cmd.trim());
            }
        }
        return args;
    }

    public static boolean execute(Card source, Card target) {

        if (source.hasCondition(Exhausted.class)) {
            Logger.debug(source + " is exhausted");
            return false;
        }

        if (source.hasKeyword(Keyword.PERMANENT)) {
            return CommandParser.executeCommands(source, target);
        }

        if (source.getOwner() instanceof Player) {
            Card currentCard = GameEngine.getInstance()
                    .getCurrentCard();
            if (!currentCard.hasOneKeyword(source.getKeywords())) {
                Logger.debug(currentCard + " cannot play " + source);
                return false;
            }
        }
        boolean result = CommandParser.executeCommands(source, target);
        if (result) {
            if (source.getOwner() instanceof Player) {
                GameEngine.getInstance().getPlayer().discard(source);
                Logger.debug("discarding " + source);
            }
            return true;
        } else {
            return false;
        }
    }
}
