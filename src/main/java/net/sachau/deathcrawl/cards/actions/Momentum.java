package net.sachau.deathcrawl.cards.actions;

import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.keywords.Keyword;

public class Momentum extends Card {

    public Momentum() {
        super(0 ,0);
        addKeywords(Keyword.ACTION);
        setCommand("momentum 1");
    }
}
