package net.sachau.deathcrawl.card.type;

import net.sachau.deathcrawl.card.Card;
import net.sachau.deathcrawl.card.keyword.Keyword;

public class Spell extends Card {

    public Spell() {
        super();
        addKeywords(Keyword.SPELL);
    }

    public Spell(Spell card) {
        super(card);
        addKeywords(Keyword.SPELL);
    }
}
