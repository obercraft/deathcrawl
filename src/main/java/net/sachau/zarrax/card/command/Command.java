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

    /**
     * this is the general method to execute commands, always use this method
     * @param source card that should execute its commands
     * @param target the target of the commands
     * @return command result
     */
    public static CommandResult execute(Card source, Card target) {

        // a command of an exhausted card may not be executed
        if (source.hasKeyword(Keyword.EXHAUSTED)) {
            return CommandResult.notAllowed(source + " is exhausted");
        }

        // a character always has a list of cards to choose among,
        // execute the command of the selected card
        if (source instanceof Character) {
            Character character = (Character) source;
            return execute(character.getSelectedCard(), target);
        } else if (source instanceof LimitedUsage) {
            return ((LimitedUsage) source).execute(target);
        }


        // a PERMANENT card has already been played, so need
        // to check if the source can play the card ...
        if (source.hasKeyword(Keyword.PERMANENT)) {
            return CommandParser.executeCommands(source, target);
        }

        // .. otherwise check if owner can play the card
        if (source.getOwner() instanceof Player) {
            Card currentCard = GameEngine.getInstance()
                    .getCurrentCard();
            if (!currentCard.hasOneKeyword(source.getKeywords())) {
                return CommandResult.notAllowed(currentCard + " cannot play " + source);
            }
        }

        // finally execute commands and discard source if commands were successful
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
