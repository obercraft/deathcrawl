package net.sachau.deathcrawl.cards.types;

import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.keywords.Keyword;

public class Action extends Card {

    public Action() {
        super();
        addKeywords(Keyword.ACTION);
    }

    public Action(Action action) {
        super(action);
    }
}
