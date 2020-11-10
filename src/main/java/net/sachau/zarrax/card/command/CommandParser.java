package net.sachau.zarrax.card.command;

import net.sachau.zarrax.Logger;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.type.Character;
import net.sachau.zarrax.card.type.AdvancedAction;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CommandParser {

    private static final Set<Class<? extends CardCommand>> actionClasses = new Reflections(CardCommand.class.getCanonicalName().replace("CardCommand", "commands")).getSubTypesOf(CardCommand.class);

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

        String cardCommandString = commands.get(0)
                .toLowerCase()
                .replace("_", "");
        commands.remove(0);

        CommandParameter commandParameter = new CommandParameter(commands);

        for (Class<? extends CardCommand> cardCommand : actionClasses) {
            if (cardCommandString.equalsIgnoreCase(cardCommand.getSimpleName())) {
                try {
                    CardCommand cc = cardCommand.newInstance();
                    CommandResult status = cc.isAllowed(source, target);
                    if (status != null && !status.isAllowed()) {
                        return status;
                    } else {
                        Logger.debug("$ " + cc.getClass()
                                .getSimpleName() + "@" + commandParameter.getTarget() + " (" + source + ", " + target + ")");
                        return cc.execute(source, target, commandParameter);
                    }
                } catch (Exception e) {
                    return CommandResult.notAllowed("failed to execute " + cardCommandString + ": " + e.getMessage());
                }
            }
        }
        return CommandResult.notAllowed("failed to execute " + cardCommandString + ": command not found");
    }

//        switch (command) {

//            case POISON_ITEM: {
//                // TODO msachau
////                int amount = new Integer(args[1]);
////                Set<Keyword> keywords = new HashSet<>();
////                for (int i = 2; i < args.length; i++) {
////                    keywords.add(Keyword.valueOf(args[i]));
////                }
////
////
////                if (target.hasAllKeywords(keywords)) {
////                    target.getConditions()
////                            .add(new Poisonous(amount));
////                    return true;
////                }
////                return false;
//                return CommandResult.notAllowed("POISON ITEM not implemented");
//            }
//            case MOMENTUM: {
//                if (source.getOwner() instanceof Player) {
//                    Player player = (Player) source.getOwner();
//                    int m = player.getMomentum() + new Integer(commands.get(1));
//                    player.setMomentum(m);
//                    return CommandResult.successful();
//                }
//                return CommandResult.notAllowed("MOMENTUM not allowed");
//            }
//            default:
//                return CommandResult.notAllowed("command " + command + " is not allowed");

//            case PLAY_TO_PARTY: {
//                Player p = source.getPlayer();
//                if (p != null) {
//                    source.getEffects().add(new KeywordEffect(Keyword.PERMANENT));
//                    p.getParty()
//                            .add(source);
//                    //source.getDeck().remove(source);
//                    return CommandResult.successful();
//                }
//                return CommandResult.notAllowed("player is null");
//
//            }
//            case GOLD: {
//                Player p = source.getPlayer();
//                if (p != null) {
//                    int g = p.getGold() + 1;
//                    p.setGold(g);
//                }
//                return CommandResult.successful();
//            }
//
//            case PRONE: {
//                int count = commands.size() >= 3 ? new Integer( commands.get(2)) : 1;
//                List<Card> targets = CardUtils.getPossibleTargets(source, target, commandTarget, count);
//                if (targets.size() == 0) {
//                    return CommandResult.notAllowed(source + " has no targets");
//
//                }
//                for (Card t : targets) {
//                    t.getEffects().add(new KeywordEffect(Keyword.PRONE));
//                    Logger.debug(source + " prones" + t);
//                }
//                return CommandResult.successful();
//            }
//            case EXHAUST: {
//                source.getEffects().add(new KeywordEffect(Keyword.EXHAUSTED));
//                return CommandResult.successful();
//            }
//            case EXHAUST_ATTACK: {
//                if (target == null) {
//                    return CommandResult.notAllowed("target is null");
//                }
//                int count = commands.size() >= 3 ? new Integer( commands.get(2)) : 1;
//                List<Card> targets = CardUtils.getPossibleTargets(source, target, commandTarget, count);
//                if (targets.size() == 0) {
//                    return CommandResult.notAllowed(source +  " has no targets");
//
//                }
//
//                for (Card t : targets) {
//                    t.getEffects().add(new KeywordEffect(Keyword.EXHAUSTED));
//                }
//                return CommandResult.successful();
//
//            }

//            case ADD_CARD: {
//                List<Card> cards;
//                if ("hand".equals(commands.get(2).toLowerCase())) {
//                    cards = GameEngine.getInstance().getPlayer().getHand();
//                } else {
//                    cards = GameEngine.getInstance().getPlayer().getHazards();
//                }
//                Card card = Catalog.getInstance()
//                        .get(commands.get(2));
//                if (card != null && cards != null) {
//                    cards.add(CardUtils.copyCard(card));
//                    return CommandResult.successful();
//                } else {
//                    return CommandResult.notAllowed("cannot ADD Card");
//                }
//            }
//
//        }
//
//
//    }


}
