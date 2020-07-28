package net.sachau.deathcrawl.gui.card;

import net.sachau.deathcrawl.cards.Card;

import java.util.HashMap;
import java.util.Map;

public class CardTileCache {

    private static Map<Long, CardTile> tileCache = new HashMap<>();

    public static CardTile getTile(Card card, String cssClass) {
        CardTile cachedTile = tileCache.get(card.getId());
        if (cachedTile == null) {
            cachedTile = new CardTile(card,cssClass);
            tileCache.put(card.getId(), cachedTile);
        }
        return cachedTile;
    }

    public static CardTile getTile(Long id) {
        return tileCache.get(id);
    }

    public static void put(CardTile cardTile) {
        tileCache.put(cardTile.getCard().getId(), cardTile);
    }
}
