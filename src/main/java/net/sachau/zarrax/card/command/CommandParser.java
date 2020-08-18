package net.sachau.zarrax.card.command;

import net.sachau.zarrax.Logger;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.util.CardUtils;
import net.sachau.zarrax.card.catalog.Catalog;
import net.sachau.zarrax.card.effect.Prone;
import net.sachau.zarrax.card.type.AdvancedAction;
import net.sachau.zarrax.card.type.LimitedUsage;
import net.sachau.zarrax.engine.GameEngine;
import net.sachau.zarrax.engine.Player;
import net.sachau.zarrax.card.effect.Armor;
import net.sachau.zarrax.card.effect.Exhausted;
import net.sachau.zarrax.card.keyword.Keyword;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class CommandParser {

    public static boolean executeCommands(Card source, Card target) {
        if (source instanceof LimitedUsage) {
            List<Card> cards = GameEngine.getInstance().getPlayer().getParty();
            if (cards != null && cards.contains(source)) {
                LimitedUsage lu = (LimitedUsage) source;
                return lu.execute(target);
            }
        }
        return executeCommands(source.getCommand(), source, target);
    }

    public static boolean executeCommands(String commandString, Card source, Card target) {

        if (source == null) {
            Logger.debug("no source card");
            return false;
        }

        if (source instanceof AdvancedAction) {
            return ((AdvancedAction) source).execute(target);
        }

        if (StringUtils.isEmpty(source.getCommand()) && StringUtils.isEmpty(commandString)) {
            Logger.debug("no command given");
            return false;
        }

        String cmds = StringUtils.isNotEmpty(commandString) ? commandString : source.getCommand();
        String[] commands = cmds.trim().toLowerCase().split(";", -1);

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

        CommandTarget commandTarget = getCommandTarget(commands.size() > 1 ? commands.get(1) : "");

        boolean statusResult = command.getStatus()
                .check(source, target);

        if (!statusResult) {
            return false;
        }

        Logger.debug("$ " + command + " " + commandTarget + " (" + source +", " + target +")");
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

                int count = commands.size() >= 3 ? new Integer( commands.get(2)) : 1;
                List<Card> targets = CardUtils.getPossibleTargets(source, target, commandTarget, count);
                if (targets.size() == 0) {
                    Logger.debug(source +  " has no targets");
                    return false;
                }


                boolean result = true;
                for (Card t : targets) {
                    result &= source.attack(t, source.getDamage());

                }
                return result;
            }
            case HEAL: {

                int count = commands.size() >=3 ? new Integer(commands.get(2)) : 1;

                List<Card> targets = CardUtils.getPossibleFriendlyTargets(source, target, commandTarget, count);
                boolean result = true;
                if (target != null) {
                    for (Card t : targets) {
                        result &= source.heal(t, source.getDamage());
                    }
                }
                return result;
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
                int count = commands.size() >= 3 ? new Integer( commands.get(2)) : 1;
                List<Card> targets = CardUtils.getPossibleTargets(source, target, commandTarget, count);
                if (targets.size() == 0) {
                    Logger.debug(source + " has no targets");
                    return false;
                }
                for (Card t : targets) {
                    t.getConditions().add(new Prone());
                    Logger.debug(source + " prones" + t);

                }
                return true;
            }
            case EXHAUST: {
                source.getConditions().add(new Exhausted());
                return true;
            }
            case EXHAUST_ATTACK: {
                if (target == null) {
                    return false;
                }
                int count = commands.size() >= 3 ? new Integer( commands.get(2)) : 1;
                List<Card> targets = CardUtils.getPossibleTargets(source, target, commandTarget, count);
                if (targets.size() == 0) {
                    Logger.debug(source +  " has no targets");
                    return false;
                }

                for (Card t : targets) {
                    t.getConditions().add(new Exhausted());
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

    private static CommandTarget getCommandTarget(String s) {
        if (StringUtils.isEmpty(s)) {
            return CommandTarget.TARGET;
        }
        if (s.trim().toLowerCase().contains("all")) {
            return CommandTarget.ALL;
        }

        if (s.trim().toLowerCase().contains("self")) {
            return CommandTarget.SELF;
        }

        if (s.trim().toLowerCase().contains("rand")) {
            return CommandTarget.RANDOM;
        }

        if (s.trim().toLowerCase().contains("adj")) {
            return CommandTarget.ADJACENT;
        }


        return CommandTarget.TARGET;
    }
}
