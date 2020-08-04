package net.sachau.deathcrawl.card.type;

import net.sachau.deathcrawl.card.Card;
import net.sachau.deathcrawl.card.keyword.Keyword;

public class Action extends Card {

    public Action() {
        super();
        addKeywords(Keyword.ACTION);
    }

    public Action(Action action) {
        super(action);
    }
}
