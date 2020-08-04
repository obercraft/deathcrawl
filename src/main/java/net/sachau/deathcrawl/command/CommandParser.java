package net.sachau.deathcrawl.command;

import net.sachau.deathcrawl.Logger;
import net.sachau.deathcrawl.card.Card;
import net.sachau.deathcrawl.card.CardUtils;
import net.sachau.deathcrawl.card.catalog.Catalog;
import net.sachau.deathcrawl.card.type.AdvancedAction;
import net.sachau.deathcrawl.engine.GameEngine;
import net.sachau.deathcrawl.engine.Player;
import net.sachau.deathcrawl.card.effect.Armor;
import net.sachau.deathcrawl.card.effect.Exhausted;
import net.sachau.deathcrawl.card.keyword.Keyword;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CommandParser {

    public static boolean executeCommand(Card source, Card target) {

        if (source instanceof AdvancedAction) {
            return ((AdvancedAction) source).execute(target);
        }

        if (source == null || source.getCommand() == null) {
            return false;
        }

        String[] commands = source.getCommand()
                .split(";", -1);

        if (commands == null || commands.length == 0) {
            return false;
        }
        boolean result = false;
        for (String c : commands) {
            result = executeCommand(c.trim(), source, target);
            if (result == false) {
                return false;
            }
        }
        return result;
    }

    private static boolean executeCommand(String commandString, Card source, Card target) {

        List<String> commands = Command.getArguments(commandString);

        if (commands == null || commands.size() < 1) {
            return false;
        }

        Commands command = Commands.valueOf(commands.get(0).toUpperCase()
                .trim());

        switch (command) {
            case DRAW: {
                int amount = 1;
                if (commands.size() > 1) {
                    amount = Integer.parseInt(commands.get(1));
                }
                Player p = source.getPlayer();
                if (p != null) {
                    p.draw(amount);
                }
                return true;
            }
            case ATTACK: {
                if (target == null) {
                    return false;
                }
                if (target.getOwner() instanceof Player && source.getOwner() instanceof Player) {
                    return false;
                }
                return source.attack(target, source.getDamage());
            }
            case RANDOM_ATTACK: {

                List<Card> targets = CardUtils.getPossibleTargets(source);
                if (targets.size() == 0) {
                    Logger.debug(source +  " has no targets");
                    return false;
                }
                int count = new Integer(commands.get(1));
                int amount = 1;

                if (commands.size() > 2) {
                    amount = Integer.parseInt(commands.get(2));
                }

                for (int i = 0; i < count; i++) {
                    Card targetCard = targets.get(ThreadLocalRandom.current().nextInt(0, targets.size()));
                    source.attack(targetCard, amount);
                }
                return true;
            }
            case HEAL: {
                if (target != null) {
                    return target.heal(new Integer(commands.get(1)));
                }
                return false;
            }
            case SHIELD: {
                if (target != null && target.getKeywords()
                        .contains(Keyword.CREATURE)) {
                    target.getConditions()
                            .add(new Armor());
                }
                return true;
            }
            case POISON_ITEM: {
                // TODO msachau
//                int amount = new Integer(args[1]);
//                Set<Keyword> keywords = new HashSet<>();
//                for (int i = 2; i < args.length; i++) {
//                    keywords.add(Keyword.valueOf(args[i]));
//                }
//
//
//                if (target.hasAllKeywords(keywords)) {
//                    target.getConditions()
//                            .add(new Poisonous(amount));
//                    return true;
//                }
//                return false;
                return false;
            }
            case MOMENTUM: {
                if (source.getOwner() instanceof Player) {
                    Player player = (Player) source.getOwner();
                    int m = player.getMomentum() + new Integer(commands.get(1));
                    player.setMomentum(m);
                    return true;
                }
                return false;
            }
            default:
                return false;

            case PLAY_TO_PARTY: {
                Player p = source.getPlayer();
                if (p != null) {
                    source.addKeywords(Keyword.PERMANENT);
                    p.getParty()
                            .add(source);
                    //source.getDeck().remove(source);
                    return true;
                }
                return false;

            }
            case GOLD: {
                Player p = source.getPlayer();
                if (p != null) {
                    int g = p.getGold() + 1;
                    p.setGold(g);
                }
                return true;
            }

            case PRONE: {
                return true;
            }
            case EXHAUST: {
                source.getConditions().add(new Exhausted());
                return true;
            }
            case EXHAUST_RANDOM: {
                Card card = CardUtils.getRandomCard(CardUtils.getPossibleTargets(GameEngine.getInstance().getPlayer().getParty()));
                if (card != null) {
                    card.getConditions().add(new Exhausted());
                }
                return true;
            }

            case ADD_CARD: {
                List<Card> cards;
                if ("hand".equals(commands.get(2).toLowerCase())) {
                    cards = GameEngine.getInstance().getPlayer().getHand();
                } else {
                    cards = GameEngine.getInstance().getPlayer().getHazards();
                }
                Card card = Catalog.getInstance()
                        .get(commands.get(2));
                if (card != null && cards != null) {
                    cards.add(CardUtils.copyCard(card));
                    return true;
                } else {
                    return false;
                }
            }

        }


    }
}
