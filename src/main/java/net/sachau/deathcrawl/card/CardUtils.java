package net.sachau.deathcrawl.card;

import net.sachau.deathcrawl.Logger;
import net.sachau.deathcrawl.card.Card;
import net.sachau.deathcrawl.card.keyword.Keyword;
import net.sachau.deathcrawl.engine.GameEngine;
import net.sachau.deathcrawl.engine.Player;

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
        return cards.get(ThreadLocalRandom.current().nextInt(0, cards.size()));
    }

    public static List<Card> getPossibleTargets(List<Card> possibleTargets) {
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

    public static List<Card> getPossibleTargets(Card attackingCard) {
        if (attackingCard == null) {
            return new ArrayList<>();
        }
        List<Card> possibleTargets;
        if (attackingCard.getOwner() instanceof Player) {
            possibleTargets = GameEngine.getInstance().getPlayer().getHazards();

        } else {
            possibleTargets  = GameEngine.getInstance().getPlayer().getParty();
        }
        return getPossibleTargets(possibleTargets);

    }
}
