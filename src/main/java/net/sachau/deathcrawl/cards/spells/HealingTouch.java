package net.sachau.deathcrawl.cards.spells;

import net.sachau.deathcrawl.cards.Basic;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.keywords.Keyword;

@Basic
public class HealingTouch extends Card {

	public HealingTouch() {
		super(0, 0);
		setCommand("heal 2");
		addKeywords(Keyword.CLERIC);

	}


}

