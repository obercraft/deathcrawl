package net.sachau.zarrax.engine;

import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.keyword.Keyword;
import net.sachau.zarrax.card.type.Monster;
import net.sachau.zarrax.card.command.CommandParser;


import java.util.Observable;
import java.util.concurrent.ThreadLocalRandom;

public class GameAI extends Observable {

    @Deprecated
    public static void execute(Player player) {

        for (Card card : player.getHazards()) {
            execute(player, card);
        }

    }

    public static void execute(Player player, Card card) {
        if (card instanceof Monster) {
            Monster monsterCard = (Monster) card;

            if (monsterCard.isRanged()) {
                int randomNum = ThreadLocalRandom.current()
                        .nextInt(0, player.getParty().size());

                Card target = player.getParty().get(randomNum);

                CommandParser.executeCommands(monsterCard, target);
            } else {

                Card target = null;

                // first check for guards
                for (Card character : player.getParty()) {
                    if (character.isAlive() && character.hasKeyword(Keyword.GUARDED)) {
                        target = character;
                    }
                }

                // 2. then attack armor
                if (target == null) {
                    for (Card character : player.getParty()) {
                        if (character.isAlive() && character.hasKeyword(Keyword.ARMOR)) {
                            target = character;
                        }
                    }
                }

                // 3. attack non-stealth
                if (target == null) {
                    for (Card character : player.getParty()) {
                        if (character.isAlive() && !character.hasKeyword(Keyword.STEALTH)) {
                            target = character;
                        }
                    }
                }

                if (target != null) {
                    CommandParser.executeCommands(monsterCard, target);
                }
            }


        }
    }

}
