package net.sachau.deathcrawl.cards.classes;

import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.CardEffect;
import net.sachau.deathcrawl.effects.Armored;
import net.sachau.deathcrawl.effects.Stealthy;
import net.sachau.deathcrawl.keywords.Keyword;

public class Thief extends Card {

	public Thief() {
		super("Thief", 7, 0);
		addKeywords(Keyword.CREATURE, Keyword.ROGUE);
		addEffect(CardEffect.Phase.PREPARE, new Stealthy());
	}


}

