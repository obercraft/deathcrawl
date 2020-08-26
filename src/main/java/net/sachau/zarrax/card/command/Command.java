package net.sachau.zarrax.card.command;

import net.sachau.zarrax.Logger;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.keyword.Keyword;
import net.sachau.zarrax.card.type.Character;
import net.sachau.zarrax.card.type.LimitedUsage;
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

    public static CommandResult execute(Card source, Card target) {

        if (source.hasKeyword(Keyword.EXHAUSTED)) {
            return CommandResult.notAllowed(source + " is exhausted");
        }


        if (source instanceof Character) {
            Character character = (Character) source;
            return execute(character.getSelectedCard(), target);
        } else if (source instanceof LimitedUsage) {
            return ((LimitedUsage) source).execute(target);
        }


        if (source.hasKeyword(Keyword.PERMANENT)) {
            return CommandParser.executeCommands(source, target);
        }

        if (source.getOwner() instanceof Player) {
            Card currentCard = GameEngine.getInstance()
                    .getCurrentCard();
            if (!currentCard.hasOneKeyword(source.getKeywords())) {
                return CommandResult.notAllowed(currentCard + " cannot play " + source);
            }
        }
        CommandResult result = CommandParser.executeCommands(source, target);
        if (result.isSuccessful()) {
            if (source.getOwner() instanceof Player) {
                GameEngine.getInstance()
                        .getPlayer()
                        .discard(source);
                Logger.debug("discarding " + source);
            }
        }
        return result;

    }
}
