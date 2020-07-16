package net.sachau.deathcrawl.cards.classes;

import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.effects.Armored;
import net.sachau.deathcrawl.keywords.Keyword;

public class Wizard extends Card {

	public Wizard() {
		super("Wizard", 6, 0);
		addKeywords(Keyword.CREATURE, Keyword.CASTER, Keyword.WIZARD);
	}
}

