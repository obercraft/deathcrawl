package net.sachau.deathcrawl.cards.types;

import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.keywords.Keyword;

public class Item extends Card {

    public Item() {
        super();
        addKeywords(Keyword.ITEM);
    }

    public Item(Item card) {
        super(card);
        addKeywords(Keyword.ITEM);
    }
}
