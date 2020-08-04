package net.sachau.deathcrawl.command;

import net.sachau.deathcrawl.Logger;
import net.sachau.deathcrawl.card.Card;
import net.sachau.deathcrawl.card.effect.Exhausted;
import net.sachau.deathcrawl.card.keyword.Keyword;
import net.sachau.deathcrawl.card.type.AdvancedAction;
import net.sachau.deathcrawl.engine.GameEngine;
import net.sachau.deathcrawl.engine.Player;
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
                    return c.getType();
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
            return CommandParser.executeCommand(source, target);
        }

        if (source.getOwner() instanceof Player) {
            Card currentCard = GameEngine.getInstance()
                    .getCurrentCard();
            if (!currentCard.hasOneKeyword(source.getKeywords())) {
                Logger.debug(currentCard + " cannot play " + source);
                return false;
            }
        }
        boolean result = CommandParser.executeCommand(source, target);
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
