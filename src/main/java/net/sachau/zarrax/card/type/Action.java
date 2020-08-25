package net.sachau.zarrax.card.type;

import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.keyword.Keyword;

public class Action extends Card {

    public Action() {
        super();
        addKeyword(Keyword.ACTION);
    }

    public Action(Action action) {
        super(action);
    }
}
