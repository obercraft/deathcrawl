package net.sachau.deathcrawl.cards.items;

import net.sachau.deathcrawl.cards.Basic;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.keywords.Keyword;

@Basic
public class ThrowingKnife extends Card {

	public ThrowingKnife() {
		super("Throwing Knife", 1, 1);
		addKeywords(Keyword.SIMPLE, Keyword.WEAPON, Keyword.ITEM, Keyword.RANGED);
		setCommand("attack 1");
	}

}
