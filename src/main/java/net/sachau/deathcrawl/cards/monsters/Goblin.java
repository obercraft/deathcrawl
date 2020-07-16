package net.sachau.deathcrawl.cards.monsters;

import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.keywords.Keyword;

public class Goblin extends Card {

	public Goblin() {
		super("Goblin", 3, 1);
		setCommand("attack 1");
		addKeywords(Keyword.CREATURE, Keyword.MONSTER);
		this.setHits(3);

	}



}
