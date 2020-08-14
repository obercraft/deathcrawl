package net.sachau.zarrax.card.type;

import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.keyword.Keyword;

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
