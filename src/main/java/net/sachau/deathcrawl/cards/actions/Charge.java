package net.sachau.deathcrawl.cards.actions;

import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.keywords.Keyword;

public class Charge extends Card {
        public Charge() {
            super("Charge", 0 ,3);
            addKeywords(Keyword.MOMENTUM, Keyword.ACTION);
            setCommand("attack");
        }

}
