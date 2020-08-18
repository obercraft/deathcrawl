package net.sachau.zarrax.engine;

import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.type.Monster;
import net.sachau.zarrax.card.command.CommandParser;
import net.sachau.zarrax.card.effect.Armor;
import net.sachau.zarrax.card.effect.Guard;
import net.sachau.zarrax.card.effect.Stealth;

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
                    if (character.isAlive() && character.hasCondition(Guard.class)) {
                        target = character;
                    }
                }

                // 2. then attack armor
                if (target == null) {
                    for (Card character : player.getParty()) {
                        if (character.isAlive() && character.hasCondition(Armor.class)) {
                            target = character;
                        }
                    }
                }

                // 3. attack non-stealth
                if (target == null) {
                    for (Card character : player.getParty()) {
                        if (character.isAlive() && !character.hasCondition(Stealth.class)) {
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
