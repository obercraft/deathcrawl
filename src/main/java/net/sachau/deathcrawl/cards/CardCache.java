package net.sachau.deathcrawl.cards;

import java.util.HashMap;
import java.util.Map;

public class CardCache {

    private static Map<Long, Card> cache = new HashMap<>();

    public static Card get(long id) {
        return cache.get(id);
    }


    public static Card put(Card card) {
        cache.put(card.getId(), card);
        return card;
    }
}
