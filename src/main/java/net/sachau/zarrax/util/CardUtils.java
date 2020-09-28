package net.sachau.zarrax.util;

import net.sachau.zarrax.Logger;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.command.CommandTarget;
import net.sachau.zarrax.card.keyword.Keyword;
import net.sachau.zarrax.engine.ApplicationContext;
import net.sachau.zarrax.engine.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CardUtils {

    public static Card copyCard(Card card) {
        try {
            return card.getClass()
                    .getDeclaredConstructor(card.getClass())
                    .newInstance(card);
        } catch (Exception e) {
            Logger.error("failed to copy card", e);
            return null;
        }
    }

    public static Card getRandomCard(List<Card> cards) {
        if (cards == null || cards.size() == 0) {
            return null;
        }
        return cards.get(ThreadLocalRandom.current()
                .nextInt(0, cards.size()));
    }

    private static List<Card> getPossibleTargets(List<Card> possibleTargets) {
        List<Card> resultTargets = new ArrayList<>();
        if (possibleTargets == null) {
            return resultTargets;
        }

        for (Card c : possibleTargets) {
            if (c.hasKeyword(Keyword.CREATURE) && c.isAlive()) {
                resultTargets.add(c);
            }
        }
        return resultTargets;

    }

    public static List<Card> getPossibleTargets(Card attackingCard, Card targetCard, CommandTarget commandTarget, int count) {
        List<Card> resultList = new ArrayList<>();

        if (attackingCard == null) {
            return resultList;
        }
        if (CommandTarget.SELF.equals(commandTarget)) {
            resultList.add(attackingCard);
            return resultList;
        }

        if (commandTarget.TARGET.equals(commandTarget)) {
            resultList.add(targetCard);
            return resultList;
        }

        List<Card> possibleTargets;
        if (attackingCard.getOwner() instanceof Player) {
            possibleTargets = ApplicationContext.getPlayer()
                    .getHazards();

        } else {
            possibleTargets = ApplicationContext
                    .getPlayer()
                    .getParty();
        }
        List<Card> targets = getPossibleTargets(possibleTargets);
        if (targets == null || targets.size() == 0) {
            return resultList;
        }
        if (CommandTarget.ALL.equals(commandTarget)) {
            resultList.addAll(targets);
            return resultList;
        } else if (CommandTarget.RANDOM.equals(commandTarget)) {
            for (int i = 0; i < count; i++) {
                resultList.add(targets.get(ThreadLocalRandom.current()
                        .nextInt(0, targets.size())));
            }
            return resultList;
        } else if (CommandTarget.ADJACENT.equals(commandTarget)) {
            int index = -1;
            int i = 0;
            for (Card card : targets) {
                if (targetCard == card) {
                    index = i;
                }
                i++;
            }
            if (index > -1) {
                resultList.add(targetCard);
                if (index > 0 && targets.size() > 1) {
                    resultList.add(targets.get(index - 1));
                }
            }
            if (index < targets.size() - 2) {
                resultList.add(targets.get(index + 1));
            }
            return resultList;
        }

        return resultList;

    }

    public static List<Card> getPossibleFriendlyTargets(Card source, Card target, CommandTarget commandTarget, int count) {
        List<Card> targets = new ArrayList<>();
        switch (commandTarget) {
            default:
            case SELF:
                targets.add(source);
                break;
            case TARGET:
                targets.add(target);
                break;
            case ALL: {
                List<Card> cards = (source.getOwner() instanceof Player) ? ApplicationContext
                        .getPlayer()
                        .getParty() : ApplicationContext
                        .getPlayer()
                        .getHazards();
                if (cards != null) {
                    targets.addAll(cards);
                }
            }
            break;
            case RANDOM: {
                List<Card> cards = (source.getOwner() instanceof Player) ? ApplicationContext
                        .getPlayer()
                        .getParty() : ApplicationContext
                        .getPlayer()
                        .getHazards();
                if (cards != null) {
                    for (int i = 0; i< count; i++) {
                        targets.add(cards.get(ThreadLocalRandom.current().nextInt(0, cards.size())));
                    }
                }
            }
            break;
            case ADJACENT: {
                List<Card> cards = (source.getOwner() instanceof Player) ? ApplicationContext
                        .getPlayer()
                        .getParty() : ApplicationContext
                        .getPlayer()
                        .getHazards();
                if (cards != null) {
                    targets.add(target);
                    int centerId = -1;
                    int i = 0;
                    for (Card c : cards) {
                        if (c == target) {
                            centerId = i;
                        }
                        i++;
                    }
                    if (centerId > 1) {
                        targets.add(cards.get(centerId -1 ));
                    }
                    if (centerId < cards.size() -2) {
                        targets.add(cards.get(centerId +1 ));
                    }
                }
            }
            break;
        }
        return targets;


    }
}
