package net.sachau.deathcrawl.cards.items;

import net.sachau.deathcrawl.cards.Basic;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.keywords.Keyword;

@Basic
public class Gold extends Card {

	public Gold() {
		super( 1, 0);
		addKeywords(Keyword.SIMPLE, Keyword.ITEM);
		setCommand("gold 1");
	}

}
