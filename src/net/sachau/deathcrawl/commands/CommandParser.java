package net.sachau.deathcrawl.commands;

import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.dto.Player;

public class CommandParser {

    enum Commands {
        ATTACK,
    }

    public static void executeCommand(Card source, Card target) {
        if (source == null || source.getCommand() == null) {
            return;
        }

        String[] args = source.getCommand()
                .split("\\ +", -1);

        if (args == null || args.length < 1) {
            return;
        }

        Commands command = Commands.valueOf(args[0].toUpperCase()
                .trim());

        switch (command) {
            case ATTACK:

                if (target != null) {
                    source.attack(target, new Integer(args[1]));
                } else {
                    source.attack(target, new Integer(args[1]));
                }
                break;
        }


    }
}
