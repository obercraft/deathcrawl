package net.sachau.deathcrawl.card.type;

import net.sachau.deathcrawl.card.Card;
import net.sachau.deathcrawl.card.keyword.Keyword;

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
