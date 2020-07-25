package net.sachau.deathcrawl.cards.items;

import net.sachau.deathcrawl.cards.Basic;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.keywords.Keyword;

@Basic
public class HealingPotion extends Card {

	public HealingPotion() {
		super( 1, 0);
		addKeywords(Keyword.SIMPLE, Keyword.POTION, Keyword.ITEM);
		setCommand("heal 1");
	}

}
