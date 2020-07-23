package net.sachau.deathcrawl;

import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.MonsterCard;
import net.sachau.deathcrawl.commands.CommandParser;
import net.sachau.deathcrawl.conditions.Armor;
import net.sachau.deathcrawl.conditions.Guard;
import net.sachau.deathcrawl.conditions.Stealth;
import net.sachau.deathcrawl.dto.Player;

import java.util.Observable;
import java.util.concurrent.ThreadLocalRandom;

public class GameAI extends Observable {

    public static void execute(Player player) {

        for (Card card : player.getHazard().getCards()) {

            if (card instanceof MonsterCard) {
                MonsterCard monsterCard = (MonsterCard) card;

                if (monsterCard.isRanged()) {
                    int randomNum = ThreadLocalRandom.current()
                            .nextInt(0, player.getParty().size());

                    Card target = player.getParty().getCards().get(randomNum);

                    CommandParser.executeCommand(monsterCard, target);
                } else {

                    Card target = null;

                    // first check for guards
                    for (Card character : player.getParty().getCards()) {
                        if (character.isAlive() && character.hasCondition(Guard.class)) {
                            target = character;
                        }
                    }

                    // 2. then attack armor
                    if (target == null) {
                        for (Card character : player.getParty().getCards()) {
                            if (character.isAlive() && character.hasCondition(Armor.class)) {
                                target = character;
                            }
                        }
                    }

                    // 3. attack non-stealth
                    if (target == null) {
                        for (Card character : player.getParty().getCards()) {
                            if (character.isAlive() && !character.hasCondition(Stealth.class)) {
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
