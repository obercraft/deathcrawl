package net.sachau.deathcrawl.cards.items;

import net.sachau.deathcrawl.cards.Basic;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.keywords.Keyword;

@Basic
public class Poison extends Card {

	public Poison() {
		super("Poison", 1, 1);
		addKeywords(Keyword.ROGUE, Keyword.SIMPLE, Keyword.ITEM);
		setCommand("poison_item 2 SIMPLE WEAPON");
	}

}
