package net.sachau.deathcrawl.cards.items;

import net.sachau.deathcrawl.cards.Basic;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.keywords.Keyword;

@Basic
public class HealingPotion extends Card {

	public HealingPotion() {
		super("Healing Potion", 1, 0);
		addKeywords(Keyword.BASIC, Keyword.SIMPLE, Keyword.POTION, Keyword.ITEM);
		setText("Healing Potion");
		setCommand("heal 1");
	}

}
