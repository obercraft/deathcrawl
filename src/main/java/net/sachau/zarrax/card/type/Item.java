package net.sachau.zarrax.card.type;

import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.keyword.Keyword;

public class Item extends Card {

    public Item() {
        super();
        addKeyword(Keyword.ITEM);
    }

    public Item(Item card) {
        super(card);
        addKeyword(Keyword.ITEM);
    }
}
