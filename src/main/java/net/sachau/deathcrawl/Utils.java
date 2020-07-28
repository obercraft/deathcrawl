package net.sachau.deathcrawl;

import net.sachau.deathcrawl.cards.Card;

public class Utils {

    public static Card copyCard(Card card) throws Exception {
        return card.getClass().getDeclaredConstructor(card.getClass()).newInstance(card);
    }
}
