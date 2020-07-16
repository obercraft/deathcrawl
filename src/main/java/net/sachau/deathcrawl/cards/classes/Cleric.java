package net.sachau.deathcrawl.cards.classes;

import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.keywords.Keyword;

public class Cleric extends Card {

	public Cleric() {
		super("Cleric", 7, 0);
		addKeywords(Keyword.CREATURE, Keyword.CASTER, Keyword.CLERIC);

	}


}

