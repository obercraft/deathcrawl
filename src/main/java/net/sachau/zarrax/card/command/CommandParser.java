package net.sachau.zarrax.card.command;

import net.sachau.zarrax.Logger;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.effect.KeywordEffect;
import net.sachau.zarrax.card.type.Character;
import net.sachau.zarrax.util.CardUtils;
import net.sachau.zarrax.card.catalog.Catalog;
import net.sachau.zarrax.card.type.AdvancedAction;
import net.sachau.zarrax.engine.GameEngine;
import net.sachau.zarrax.engine.Player;
import net.sachau.zarrax.card.keyword.Keyword;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CommandParser {

//    public static boolean executeCommands(Card source, Card target) {
//        if (source instanceof LimitedUsage) {
//            List<Card> cards = GameEngine.getInstance().getPlayer().getParty();
//            if (cards != null && cards.contains(source)) {
//                LimitedUsage lu = (LimitedUsage) source;
//                return lu.execute(target);
//            }
//        }
//        return executeCommands(source.getCommand(), source, target);
//    }

    public static CommandResult executeCommands(Card source, Card target) {

        if (source == null) {
            return CommandResult.notAllowed("no source card");
        }

        if (source instanceof Character) {
            Character character = (Character) source;
            return executeCommands(character.getSelectedCard(), target);
        }

        if (source instanceof AdvancedAction) {
            return ((AdvancedAction) source).execute(target);
        }

        if (StringUtils.isEmpty(source.getCommand())) {
            return CommandResult.notAllowed("no command given");
        }

        String[] commands = source.getCommand().trim().toLowerCase().split(";", -1);

        if (commands == null || commands.length == 0) {
            return CommandResult.notAllowed("no commands given");
        }
        List<CommandResult> result = new ArrayList<>();
        for (String c : commands) {
            CommandResult commandResult = executeCommand(c.trim(), source, target);
            result.add(commandResult);
            if (commandResult.failed()) {
                return commandResult;
            }
        }
        return CommandResult.build(result);
    }

    private static CommandResult executeCommand(String commandString, Card source, Card target) {

        List<String> commands = Command.getArguments(commandString);

        if (commands == null || commands.size() < 1) {
            return CommandResult.notAllowed("command size is 0");
        }

        Commands command = Commands.valueOf(commands.get(0).toUpperCase()
                .trim());

        CommandTarget commandTarget = getCommandTarget(commands.size() > 1 ? commands.get(1) : "");

        CommandResult statusResult = command.getStatus()
                .check(source, target);

        if (!statusResult.isAllowed()) {
            return statusResult;
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
                return CommandResult.successful();
            }
            case ATTACK: {
                if (target == null) {
                    return CommandResult.notAllowed("target is null");
                }

                int count = commands.size() >= 3 ? new Integer( commands.get(2)) : 1;
                List<Card> targets = CardUtils.getPossibleTargets(source, target, commandTarget, count);
                if (targets.size() == 0) {
                    return CommandResult.notAllowed(source +  " has no targets");
                }


                List<CommandResult> result = new ArrayList<>();
                for (Card t : targets) {
                    result.add(source.attack(t, source.getDamage()));

                }
                return CommandResult.build(result);
            }
            case HEAL: {

                int count = commands.size() >=3 ? new Integer(commands.get(2)) : 1;

                List<Card> targets = CardUtils.getPossibleFriendlyTargets(source, target, commandTarget, count);
                List<CommandResult> result = new ArrayList<>();
                if (target != null) {
                    for (Card t : targets) {
                        result.add(source.heal(t, source.getDamage()));
                    }
                }
                return CommandResult.build(result);
            }
            case SHIELD: {
                if (target != null && target.getKeywords()
                        .contains(Keyword.CREATURE)) {
                    target.getEffects().add(new KeywordEffect(Keyword.ARMOR, target));
                }
                return CommandResult.successful();
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
                return CommandResult.notAllowed("POISON ITEM not implemented");
            }
            case MOMENTUM: {
                if (source.getOwner() instanceof Player) {
                    Player player = (Player) source.getOwner();
                    int m = player.getMomentum() + new Integer(commands.get(1));
                    player.setMomentum(m);
                    return CommandResult.successful();
                }
                return CommandResult.notAllowed("MOMENTUM not allowed");
            }
            default:
                return CommandResult.notAllowed("command " + command + " is not allowed");

            case PLAY_TO_PARTY: {
                Player p = source.getPlayer();
                if (p != null) {
                    source.getEffects().add(new KeywordEffect(Keyword.PERMANENT));
                    p.getParty()
                            .add(source);
                    //source.getDeck().remove(source);
                    return CommandResult.successful();
                }
                return CommandResult.notAllowed("player is null");

            }
            case GOLD: {
                Player p = source.getPlayer();
                if (p != null) {
                    int g = p.getGold() + 1;
                    p.setGold(g);
                }
                return CommandResult.successful();
            }

            case PRONE: {
                int count = commands.size() >= 3 ? new Integer( commands.get(2)) : 1;
                List<Card> targets = CardUtils.getPossibleTargets(source, target, commandTarget, count);
                if (targets.size() == 0) {
                    return CommandResult.notAllowed(source + " has no targets");

                }
                for (Card t : targets) {
                    t.getEffects().add(new KeywordEffect(Keyword.PRONE));
                    Logger.debug(source + " prones" + t);
                }
                return CommandResult.successful();
            }
            case EXHAUST: {
                source.getEffects().add(new KeywordEffect(Keyword.EXHAUSTED));
                return CommandResult.successful();
            }
            case EXHAUST_ATTACK: {
                if (target == null) {
                    return CommandResult.notAllowed("target is null");
                }
                int count = commands.size() >= 3 ? new Integer( commands.get(2)) : 1;
                List<Card> targets = CardUtils.getPossibleTargets(source, target, commandTarget, count);
                if (targets.size() == 0) {
                    return CommandResult.notAllowed(source +  " has no targets");

                }

                for (Card t : targets) {
                    t.getEffects().add(new KeywordEffect(Keyword.EXHAUSTED));
                }
                return CommandResult.successful();

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
                    return CommandResult.successful();
                } else {
                    return CommandResult.notAllowed("cannot ADD Card");
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
