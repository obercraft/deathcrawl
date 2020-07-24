package net.sachau.deathcrawl.cards.items;

import net.sachau.deathcrawl.cards.Basic;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.keywords.Keyword;

@Basic
public class Knife extends Card {

	public Knife() {
		super("Knife", 1, 1);
		addKeywords(Keyword.SIMPLE, Keyword.WEAPON, Keyword.ITEM);
		setText("attack target for 1 damage");
		setCommand("attack 1");
	}

}
