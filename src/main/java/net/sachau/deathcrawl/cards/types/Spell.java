package net.sachau.deathcrawl.cards.types;

import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.keywords.Keyword;

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
