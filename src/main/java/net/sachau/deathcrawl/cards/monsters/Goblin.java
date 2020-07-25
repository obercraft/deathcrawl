package net.sachau.deathcrawl.cards.monsters;

import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.MonsterCard;
import net.sachau.deathcrawl.keywords.Keyword;

public class Goblin extends MonsterCard {

	public Goblin() {
		super(3, 1, 1, 1);
		setCommand("attack 1");
		addKeywords(Keyword.CREATURE, Keyword.MONSTER);
		this.setHits(3);

	}



}
