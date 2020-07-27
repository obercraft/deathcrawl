package net.sachau.deathcrawl;

import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.types.Monster;
import net.sachau.deathcrawl.commands.CommandParser;
import net.sachau.deathcrawl.dto.Player;
import net.sachau.deathcrawl.effects.Armored;
import net.sachau.deathcrawl.effects.Guarded;
import net.sachau.deathcrawl.effects.Stealthy;

import java.util.Observable;
import java.util.concurrent.ThreadLocalRandom;

public class GameAI extends Observable {

    public static void execute(Player player) {

        for (Card card : player.getHazard().getCards()) {

            if (card instanceof Monster) {
                Monster monsterCard = (Monster) card;

                if (monsterCard.isRanged()) {
                    int randomNum = ThreadLocalRandom.current()
                            .nextInt(0, player.getParty().size());

                    Card target = player.getParty().getCards().get(randomNum);

                    CommandParser.executeCommand(monsterCard, target);
                } else {

                    Card target = null;

                    // first check for guards
                    for (Card character : player.getParty().getCards()) {
                        if (character.isAlive() && character.hasCondition(Guarded.class)) {
                            target = character;
                        }
                    }

                    // 2. then attack armor
                    if (target == null) {
                        for (Card character : player.getParty().getCards()) {
                            if (character.isAlive() && character.hasCondition(Armored.class)) {
                                target = character;
                            }
                        }
                    }

                    // 3. attack non-stealth
                    if (target == null) {
                        for (Card character : player.getParty().getCards()) {
                            if (character.isAlive() && !character.hasCondition(Stealthy.class)) {
                                target = character;
                            }
                        }
                    }

                    if (target != null) {
                        CommandParser.executeCommand(monsterCard, target);
                    }
                }


            }
        }

    }

}
