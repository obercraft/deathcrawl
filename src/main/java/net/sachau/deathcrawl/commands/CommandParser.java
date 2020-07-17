package net.sachau.deathcrawl.commands;

import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.Deck;
import net.sachau.deathcrawl.cards.items.Poison;
import net.sachau.deathcrawl.conditions.Armor;
import net.sachau.deathcrawl.conditions.Condition;
import net.sachau.deathcrawl.conditions.Poisonous;
import net.sachau.deathcrawl.dto.Player;
import net.sachau.deathcrawl.keywords.Keyword;

import java.util.HashSet;
import java.util.Set;

public class CommandParser {

    enum Commands {
        ATTACK,
        RANDOM_ATTACK_MANY,
        SHIELD,
        HEAL,
        POISON_ITEM,
        MOMENTUM,
    }

    public static boolean executeCommand(Card source, Card target) {
        if (source == null || source.getCommand() == null) {
            return false;
        }

        String[] args = source.getCommand()
                .split("\\ +", -1);

        if (args == null || args.length < 1) {
            return false;
        }

        Commands command = Commands.valueOf(args[0].toUpperCase()
                .trim());

        switch (command) {
            case ATTACK:
                if (target == null) {
                    return false;
                }
                if (target.getOwner() instanceof Player) {
                    return false;
                }
                return source.attack(target, source.getDamage());
            case RANDOM_ATTACK_MANY: {
                if (target != null) {
                    if (target.getOwner() instanceof Player) {
                        return false;
                    }

                    Deck targetDeck = target.getDeck();
                    int count = new Integer(args[1]);
                    int amount = source.getDamage();
                    for (int i = 0; i < count; i++) {
                        Card targetCard = targetDeck.getRandomCard();
                        if (targetCard != null) {
                            source.attack(targetCard, amount);
                        }
                    }
                }
                return true;
            }
            case HEAL:
                if (target != null) {
                    return target.heal(new Integer(args[1]));
                }
                return false;

            case SHIELD:
                if (target != null && target.getKeywords()
                        .contains(Keyword.CREATURE)) {
                    target.getConditions()
                            .add(new Armor());
                }
                return true;

            case POISON_ITEM:
                int amount = new Integer(args[1]);
                Set<Keyword> keywords = new HashSet<>();
                for (int i = 2; i < args.length; i++) {
                    keywords.add(Keyword.valueOf(args[i]));
                }


                if (target.hasAllKeywords(keywords)) {
                    target.getConditions().add(new Poisonous(amount));
                    return true;
                }
                return false;

            case MOMENTUM:
                if (source.getOwner() instanceof Player) {
                    Player player = (Player) source.getOwner();
                    int m = player.getMomentum() + new Integer(args[1]);
                    player.setMomentum(m);
                    return true;
                }
                return false;
            default:
                return false;
        }


    }
}
