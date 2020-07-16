package net.sachau.deathcrawl.cards.spells;

import net.sachau.deathcrawl.cards.Basic;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.keywords.Keyword;

@Basic
public class HealingTouch extends Card {

	public HealingTouch() {
		super("Healing Touch", 0, 0);
		setText("Healing Touch");
		setCommand("heal 2");
		addKeywords(Keyword.CLERIC, Keyword.BASIC);

	}


}

