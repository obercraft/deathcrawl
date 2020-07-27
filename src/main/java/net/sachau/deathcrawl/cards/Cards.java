package net.sachau.deathcrawl.cards;

import java.util.*;

public class Cards {

    private static final Map<String, Card> allCards = new HashMap<>();

    private Cards() {

    }

    public static Collection<Card> allCards() {
        return allCards.values();
    }

    public static void addToAll(List<Card> cards) {
        if (cards != null || cards.size() > 0) {
            for (Card card : cards) {
                put(card);
            }

        }
    }

    public static Card get(String cardName) {
        Card card = allCards.get(cardName.toLowerCase().replaceAll("\\ ", ""));
        try {
            return (Card) card.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void put(Card card) {
        allCards.put(card.getName().toLowerCase().replaceAll("\\ ", ""), card);
    }
}
