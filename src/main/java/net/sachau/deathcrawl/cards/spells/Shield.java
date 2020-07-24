package net.sachau.deathcrawl.cards.spells;

import net.sachau.deathcrawl.cards.Basic;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.keywords.Keyword;

@Basic
public class Shield extends Card {

	public Shield() {
		super("Shield", 0, 0);
		setHits(10);
		setCommand("shield 1");
		addKeywords(Keyword.WIZARD, Keyword.SPELL);

	}


}

